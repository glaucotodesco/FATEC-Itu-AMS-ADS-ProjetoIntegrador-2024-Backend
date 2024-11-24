package br.fatec.easycoast.dtos;

import br.fatec.easycoast.entities.Square;

public record ItemResponse(
    Integer id,
    String name,
    Square square
) {

}
