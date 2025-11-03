package br.fatec.easycoast.dtos.card;

import jakarta.validation.constraints.NotNull;

public record CardRequest(
    @NotNull(message = "Active of the card can't be null")
    Boolean active
) {

}
