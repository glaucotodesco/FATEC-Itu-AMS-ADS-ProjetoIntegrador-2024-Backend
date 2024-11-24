package br.fatec.easycoast.dtos;

import java.util.List;

import br.fatec.easycoast.entities.Item;

public record SquareResponse(
    Integer id,
    String name,
    List<ItemFilter> items
) {


    
}
