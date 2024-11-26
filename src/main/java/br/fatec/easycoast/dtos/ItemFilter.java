package br.fatec.easycoast.dtos;

public record ItemFilter(
    Integer id,
    String name, 
    SquareFilter square
) {
} 