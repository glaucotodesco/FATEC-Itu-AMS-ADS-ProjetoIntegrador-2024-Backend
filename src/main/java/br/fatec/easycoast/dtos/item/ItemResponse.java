package br.fatec.easycoast.dtos.item;

import br.fatec.easycoast.dtos.square.SquareResponse;


public record ItemResponse(
    Integer id,
    String name,
    SquareResponse square
) {

}
