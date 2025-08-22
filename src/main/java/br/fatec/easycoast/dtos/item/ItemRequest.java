package br.fatec.easycoast.dtos.item;

import br.fatec.easycoast.entities.Square;
import jakarta.validation.constraints.NotNull;

public record ItemRequest(
    String name,

    @NotNull(message = "Praça não pode ser nulo")
    Square square
) {

}
