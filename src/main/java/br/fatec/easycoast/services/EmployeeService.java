package br.fatec.easycoast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.employee.EmployeeRequest;
import br.fatec.easycoast.dtos.employee.EmployeeResponse;
import br.fatec.easycoast.dtos.employee.Profile;
import br.fatec.easycoast.entities.Employee;
import br.fatec.easycoast.mappers.EmployeeMapper;
import br.fatec.easycoast.repositories.EmployeeRepository;
import br.fatec.easycoast.services.exceptions.EntityGoneException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeResponse saveEmployee(EmployeeRequest employeeRequest) {
        // Mapeamento do DTO para a Entidade
        Employee savedEmployee = employeeRepository.save(EmployeeMapper.toEntity(employeeRequest));
        return EmployeeMapper.toDto(savedEmployee);
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
        Employee employee = employeeRepository.findById(id)
                               .orElseThrow(() -> new EntityNotFoundException("Employee not Found!"));
        if (employee.getProfile() == Profile.DEACTIVATED) throw new EntityGoneException("Employee Deactivated!");
        return EmployeeMapper.toDto(employee);
    }

    public void updateEmployee(Integer id, EmployeeRequest request) {
        if (employeeRepository.existsById(id)) {
            Employee employee = employeeRepository.getReferenceById(id);
            if (employee.getProfile() == Profile.DEACTIVATED) throw new EntityGoneException("Employee deactivated!");
            employee.setName(request.name());
            employee.setPhone(request.phone());
            employee.setPassword(request.phone());
            employee.setLogin(request.login());
            employee.setProfile(request.profile());
            employee.setBlocked(request.blocked());
            employeeRepository.save(employee);
        } else {
            throw new EntityNotFoundException("Employee not found!");
        }
    }

    public void deleteEmployee(int id) {
        if (employeeRepository.existsById(id)) {
            Employee employee = employeeRepository.getReferenceById(id);
            if (employee.getProfile() == Profile.DEACTIVATED) throw new EntityGoneException("Employee already deactivated!");
            employee.setProfile(Profile.DEACTIVATED);
            employeeRepository.save(employee);
        } else {
            throw new EntityNotFoundException("Employee not found!");
        }
    }
}
