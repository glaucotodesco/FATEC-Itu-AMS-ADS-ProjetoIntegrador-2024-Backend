package br.fatec.easycoast.dtos.employee;

import br.fatec.easycoast.entities.enums.Profile;

public record EmployeeResponse(
                Integer id,
                String name,
                String phone,
                String login,
                String password,
                Profile profile,
                Boolean blocked) {

}
