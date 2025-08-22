package br.fatec.easycoast.dtos.employee;

public record EmployeeRequest(
        String name,
        String phone,
        String login,
        String password,
        Profile profile,
        Boolean blocked) {

}
