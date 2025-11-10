package br.fatec.easycoast.dtos.customer;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        @NotBlank(message = "Customer name can't be blank!")
        String name,
        String phone,
        @NotNull(message = "Customer birth date can't be null")
        Instant birthDate,
        @NotBlank(message = "Customer E-mail can't be blank")
        String email,
        @NotBlank(message = "Customer password can't be blank")
        String password) {
}