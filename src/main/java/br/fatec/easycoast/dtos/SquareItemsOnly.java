package br.fatec.easycoast.dtos;

import java.util.List;

public record SquareItemsOnly(
        Integer id,
        String name,
        List<ItemsOnly> items) {
}