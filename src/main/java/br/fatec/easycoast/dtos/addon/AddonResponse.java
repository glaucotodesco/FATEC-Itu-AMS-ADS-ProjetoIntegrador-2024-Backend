package br.fatec.easycoast.dtos.addon;

import br.fatec.easycoast.dtos.item.ItemResponse;
import br.fatec.easycoast.dtos.addonCategory.AddonCategoryResponse;

public record AddonResponse(
        Integer id,
        String name,
        Float price,
        Boolean availability,
        ItemResponse item,
        AddonCategoryResponse addonCategory) {
}