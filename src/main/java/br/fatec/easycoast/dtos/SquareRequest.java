package br.fatec.easycoast.dtos;

import java.util.List;

import br.fatec.easycoast.entities.Item;

import br.fatec.easycoast.dtos.ItemFilter;

public record SquareRequest(
    String name,
    List<Item> items
) {

}
