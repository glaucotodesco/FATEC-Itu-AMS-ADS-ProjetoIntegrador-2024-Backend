package br.fatec.easycoast.dtos.employee;

import br.fatec.easycoast.entities.enums.Profile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmployeeUpdateRequest(
        @NotBlank(message = "Employee can't be blank")
        String name,
        String phone,
        @NotBlank(message = "Employee login can't be blank")
        String login,
        String password,
        @NotNull(message = "Employee profile can't be null")
        Profile profile,
        @NotNull(message = "Employee block can't be null")
        Boolean blocked) {

}
