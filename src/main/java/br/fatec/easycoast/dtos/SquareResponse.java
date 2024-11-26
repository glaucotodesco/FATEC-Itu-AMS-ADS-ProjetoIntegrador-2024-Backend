package br.fatec.easycoast.dtos;

import java.util.List;



public record SquareResponse(
    Integer id,
    String name,
    List<ItemFilter> items
) {


    
}
