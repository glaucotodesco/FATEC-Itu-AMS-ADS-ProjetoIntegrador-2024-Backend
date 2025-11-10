package br.fatec.easycoast.dtos.restaurant;

import jakarta.validation.constraints.NotBlank;

public record AboutUsSectionRequest(
    @NotBlank(message = "Header can't be blank")
    String header,
    @NotBlank(message = "Content can't be blank")
    String content
) {
}