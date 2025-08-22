package br.fatec.easycoast.dtos.addonCategory;

import java.util.List;

import br.fatec.easycoast.dtos.product.ProductFiltered;
import br.fatec.easycoast.entities.Addon;

public record AddonCategoryProductFiltered(
                Integer id,
                String name,
                AddonType type,
                ProductFiltered product,
                List<Addon> addons) {

}
