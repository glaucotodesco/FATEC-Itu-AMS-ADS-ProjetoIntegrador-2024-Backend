package br.fatec.easycoast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.employee.EmployeeRequest;
import br.fatec.easycoast.dtos.employee.EmployeeResponse;
import br.fatec.easycoast.entities.Employee;
import br.fatec.easycoast.mappers.EmployeeMapper;
import br.fatec.easycoast.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
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
                .collect(Collectors.toList());
    }

    // Função para resgatar usuário por ID
    public EmployeeResponse getEmployee(Integer id) {
        Optional<Employee> EmployeeOpt = employeeRepository.findById(id);
        return EmployeeOpt.map(EmployeeMapper::toDto).orElse(null);
    }

    public void updateEmployee(Integer id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Employee doesn't exists"));

        employee.setName(request.name());
        employee.setPhone(request.phone());
        employee.setPassword(request.phone());
        employee.setLogin(request.login());
        employee.setProfile(request.profile());
        employee.setBlocked(request.blocked());
        employeeRepository.save(employee);

    }
}
