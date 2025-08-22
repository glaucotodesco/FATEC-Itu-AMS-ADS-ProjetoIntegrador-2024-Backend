package br.fatec.easycoast.dtos.addonCategory;

import java.util.List;

import br.fatec.easycoast.dtos.addon.AddonFiltered;
import br.fatec.easycoast.dtos.product.ProductFiltered;

public record AddonCategoryFiltered(
                Integer id,
                String name,
                AddonType type,
                ProductFiltered product,
                List<AddonFiltered> addons) {

}
