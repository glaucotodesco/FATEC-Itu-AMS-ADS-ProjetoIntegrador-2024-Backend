package br.fatec.easycoast.dtos.addonCategory;

import java.util.List;

import br.fatec.easycoast.dtos.addon.AddonFiltered;

public record AddonCategoryNoProduct(
                Integer id,
                String name,
                AddonType type,
                List<AddonFiltered> addons

) {

}