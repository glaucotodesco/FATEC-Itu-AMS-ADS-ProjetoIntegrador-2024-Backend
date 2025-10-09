package br.fatec.easycoast.dtos.square;

import jakarta.validation.constraints.NotBlank;

public record SquareRequest(
        @NotBlank(message = "Square can't be blank")
        String name
) {

}
