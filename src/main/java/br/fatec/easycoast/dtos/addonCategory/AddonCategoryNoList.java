package br.fatec.easycoast.dtos.addonCategory;

import br.fatec.easycoast.dtos.product.ProductFiltered;

public record AddonCategoryNoList(
                Integer id,
                String name,
                AddonType type,
                ProductFiltered product) {

}