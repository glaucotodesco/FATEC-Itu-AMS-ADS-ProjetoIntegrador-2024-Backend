package br.fatec.easycoast.dtos.addon;

import br.fatec.easycoast.dtos.item.ItemResponse;
import br.fatec.easycoast.dtos.addonCategory.AddonCategoryNoList;

public record AddonNoList(
                Integer id,
                String name,
                Float price,
                Boolean availability,
                ItemResponse item,
                AddonCategoryNoList addonCategory) {
}