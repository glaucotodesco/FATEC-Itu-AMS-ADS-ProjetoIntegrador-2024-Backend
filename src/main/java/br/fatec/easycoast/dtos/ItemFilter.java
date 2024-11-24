package br.fatec.easycoast.dtos;
import br.fatec.easycoast.dtos.SquareFilter;
public record ItemFilter(
    Integer id,
    String name, 
    SquareFilter squareFilter
) {
} 