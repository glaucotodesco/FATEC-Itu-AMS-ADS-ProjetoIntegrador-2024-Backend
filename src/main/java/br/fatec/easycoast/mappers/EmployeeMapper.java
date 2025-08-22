package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.employee.EmployeeRequest;
import br.fatec.easycoast.dtos.employee.EmployeeResponse;
import br.fatec.easycoast.entities.Employee;

public class EmployeeMapper {

    // Método para mapear de UserRequest para a entidade User
    public static Employee toEntity(EmployeeRequest requestDTO) {
        Employee employee = new Employee();
        employee.setName(requestDTO.name());
        employee.setPhone(requestDTO.phone());
        employee.setLogin(requestDTO.login());
        employee.setPassword(requestDTO.password());
        employee.setProfile(requestDTO.profile());
        employee.setBlocked(requestDTO.blocked());
        return employee;
    }

    // Método para mapear de employee para employeeResponse
    public static EmployeeResponse toDto(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getPhone(),
                employee.getLogin(),
                employee.getPassword(),
                employee.getProfile(),
                employee.getBlocked());
    }
}