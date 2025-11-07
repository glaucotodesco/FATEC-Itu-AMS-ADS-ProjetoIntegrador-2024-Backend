package br.fatec.easycoast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fatec.easycoast.dtos.employee.EmployeeRequest;
import br.fatec.easycoast.dtos.employee.EmployeeResponse;
import br.fatec.easycoast.dtos.employee.OwnerRequest;
import br.fatec.easycoast.entities.Employee;
import br.fatec.easycoast.entities.enums.Profile;
import br.fatec.easycoast.mappers.EmployeeMapper;
import br.fatec.easycoast.repositories.EmployeeRepository;
import br.fatec.easycoast.services.auth.TokenProvider;
import br.fatec.easycoast.services.exceptions.EntityGoneException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Value("${auth.disabled}")
    private boolean authDisabled;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TokenProvider tokenProvider;
    
    @Autowired
    private InitializationService initializationService;

    public EmployeeResponse saveEmployee(EmployeeRequest employeeRequest) {
        //Limit the profile
        if(employeeRequest.profile().equals(Profile.OWNER) ||
           employeeRequest.profile().equals(Profile.CUSTOMER) ||
           employeeRequest.profile().equals(Profile.DEACTIVATED))
            throw new IllegalArgumentException("An Employee can't be saved with this profile!");
        try{
            // Mapeamento do DTO para a Entidade
            Employee savedEmployee = EmployeeMapper.toEntity(employeeRequest);
            // Encode the password
            savedEmployee.setPassword(new BCryptPasswordEncoder().encode(savedEmployee.getPassword()));
            savedEmployee = employeeRepository.save(savedEmployee);
            return EmployeeMapper.toDto(savedEmployee);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("An Employee with this login already exists!");
        }
    }
    
    //Save the Owner of the Restaurant
    @Transactional
    public void saveOwner(OwnerRequest request){
        //Delete the previous Owner account, if it exits
        if(employeeRepository.findOwner().isPresent()) employeeRepository.deleteOwner();
        Employee owner = EmployeeMapper.toOwnerEntity(request);
        //Block the profile to be Owner
        owner.setProfile(Profile.OWNER);
        //Same with block
        owner.setBlocked(false);
        //Encode the password
        owner.setPassword(new BCryptPasswordEncoder().encode(owner.getPassword()));
        employeeRepository.save(owner);
    }

    // Função para listar todos os usuários
    public List<EmployeeResponse> getEmployees() {
        List<Employee> Employees = employeeRepository.findAll();
        return Employees.stream()
                .map(EmployeeMapper::toDto)
                .filter(e -> e.profile() != Profile.DEACTIVATED)
                .collect(Collectors.toList());
    }

    // Função para resgatar usuário por ID
    public EmployeeResponse getEmployee(Integer id) {
        //Limit for employees that aren't Admin
        if(!authDisabled) if(!verifyTheProfile() && !verifyTheUser(id)) throw new IllegalArgumentException("You can't GET this employee!");
        
        Employee employee = employeeRepository.findById(id)
                               .orElseThrow(() -> new EntityNotFoundException("Employee not Found!"));
        //Check if the employee isn't deactivated
        if (employee.getProfile() == Profile.DEACTIVATED) throw new EntityGoneException("Employee Deactivated!");
        return EmployeeMapper.toDto(employee);
    }

    public EmployeeResponse getOwner(){
        return EmployeeMapper.toDto(employeeRepository.findOwner().get());
    }

    public Optional<String> updateEmployee(Integer id, EmployeeRequest request) {
        //Limit the profile
        if(request.profile().equals(Profile.OWNER) ||
           request.profile().equals(Profile.CUSTOMER) ||
           request.profile().equals(Profile.DEACTIVATED))
            throw new IllegalArgumentException("An Employee can't be saved with this profile!");
        if (employeeRepository.existsById(id)) {
            try {
                Employee employee = employeeRepository.getReferenceById(id);
                //Check if the employee isn't the owner or deactivated
                if (employee.getProfile() == Profile.DEACTIVATED)
                    throw new EntityGoneException("Employee deactivated!");
                if (employee.getProfile() == Profile.OWNER)
                    throw new IllegalArgumentException("You can't PUT this employee!");
                //Set the new Values
                employee.setName(request.name());
                employee.setPhone(request.phone());
                //Encode the password
                employee.setPassword(new BCryptPasswordEncoder().encode(request.password()));
                employee.setLogin(request.login());
                employee.setProfile(request.profile());
                employee.setBlocked(request.blocked());
                employee = employeeRepository.save(employee);
                //If the employee account is of the User, return a new authentication token
                if(verifyTheUser(id)) return Optional.of(tokenProvider.generateAccessToken(employee));
                else return Optional.empty();
            } catch (DataIntegrityViolationException e) {
                throw new IllegalArgumentException("An Employee with this login already exists!");
            }
        } else {
            throw new EntityNotFoundException("Employee not found!");
        }
    }

    //Edit the owner
    public String updateOwner(OwnerRequest request){
        try {
            Employee owner = employeeRepository.findOwner()
            .orElseThrow(() -> new EntityNotFoundException("There is no Owner User!"));
            //Set the values
            owner.setName(request.name());
            owner.setPhone(request.phone());
            //Encode the password
            owner.setPassword(new BCryptPasswordEncoder().encode(request.password()));
            owner.setLogin(request.login());
            //Mark owner as initialized
            initializationService.markOwnerAsInitialized();
            //Return a new authentication token
            return tokenProvider.generateAccessToken(employeeRepository.save(owner));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("An Employee with this login already exists!");
        }
    }

    public void deleteEmployee(int id) {
        if (employeeRepository.existsById(id)) {
            //Prevent Admin deleting their own account
            if(!authDisabled) if(verifyTheUser(id)) throw new IllegalArgumentException("You can't DELETE your own account!");
            Employee employee = employeeRepository.getReferenceById(id);
            //Check if the employee isn't the owner or deactivated
            if (employee.getProfile() == Profile.DEACTIVATED)
                throw new EntityGoneException("Employee already deactivated!");
            if (employee.getProfile() == Profile.OWNER)
                throw new IllegalArgumentException("You can't DELETE this employee!");
            //Set employee Deadtivated
            employee.setProfile(Profile.DEACTIVATED);
            employeeRepository.save(employee);
        } else {
            throw new EntityNotFoundException("Employee not found!");
        }
    }

    public void deleteOwner(){
        if(employeeRepository.findOwner().isPresent()){
            employeeRepository.deleteOwner();
        } else {
            throw new EntityNotFoundException("There is no Owner User!");
        }
    }

    private boolean verifyTheUser(int id){
        //Get the authentication of the request
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Verify if the Authentication is correct
        if(auth != null && auth.getPrincipal() instanceof UserDetails){
            //Get the User
            Employee employee = (Employee) auth.getPrincipal();
            //Check the id
            return id == employee.getId();
        } else {
            return false;
        }
    }

    private boolean verifyTheProfile(){
        //Get the authentication of the request
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Verify if the Authentication is correct
        if(auth != null && auth.getPrincipal() instanceof UserDetails){
            //Get the User
            Employee employee = (Employee) auth.getPrincipal();
            //Check if it is a Admin or the Owner
            return employee.getProfile().equals(Profile.ADMIN) || employee.getProfile().equals(Profile.OWNER);
        } else {
            return false;
        }
    }
}
