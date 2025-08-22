package br.fatec.easycoast.dtos.addon;

import br.fatec.easycoast.dtos.item.ItemResponse;

public record AddonFiltered(
        Integer id,
        String name,
        Float price,
        Boolean availability,
        ItemResponse item

) {
}