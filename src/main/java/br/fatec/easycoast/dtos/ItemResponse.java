package br.fatec.easycoast.dtos;


public record ItemResponse(
    Integer id,
    String name,
    SquareResponse square
) {

}
