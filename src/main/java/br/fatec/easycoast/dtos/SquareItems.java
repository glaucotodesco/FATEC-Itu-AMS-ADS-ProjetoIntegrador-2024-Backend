package br.fatec.easycoast.dtos;

import java.util.List;

public record SquareItems(
        Integer id,
        String name,
        List<ItemsOnly> items
) {
}