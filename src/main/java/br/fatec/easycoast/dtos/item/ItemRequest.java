package br.fatec.easycoast.dtos.item;

import br.fatec.easycoast.entities.Square;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemRequest(
    @NotBlank(message = "Item name can't be blank")
    String name,
    @NotNull(message = "Item square can't be null")
    Square square
) {

}
