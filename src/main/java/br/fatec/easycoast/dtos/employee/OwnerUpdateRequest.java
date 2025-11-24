package br.fatec.easycoast.dtos.employee;

import jakarta.validation.constraints.NotBlank;

public record OwnerUpdateRequest(
        @NotBlank(message = "Owner name can't be blank")
        String name,
        String phone,
        @NotBlank(message = "Owner login can't be blank")
        String login,
        String password
        ) {

}
