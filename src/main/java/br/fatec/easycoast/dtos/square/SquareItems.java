package br.fatec.easycoast.dtos.square;

import java.util.List;

import br.fatec.easycoast.dtos.item.ItemsOnly;

public record SquareItems(
        Integer id,
        String name,
        List<ItemsOnly> items
) {
}