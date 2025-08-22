package br.fatec.easycoast.dtos.employee;

public record EmployeeResponse(
                Integer id,
                String name,
                String phone,
                String login,
                String password,
                Profile profile,
                Boolean blocked) {

}
