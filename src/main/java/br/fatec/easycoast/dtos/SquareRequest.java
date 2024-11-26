package br.fatec.easycoast.dtos;

import java.util.List;

import br.fatec.easycoast.entities.Item;

public record SquareRequest(
    String name,
    List<Item> items
) {

}
