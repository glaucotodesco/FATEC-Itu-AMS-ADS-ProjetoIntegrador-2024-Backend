package br.fatec.easycoast.dtos.product;

import java.util.List;

import br.fatec.easycoast.dtos.addonCategory.AddonCategoryNoProduct;
import br.fatec.easycoast.dtos.item.ItemsOnly;

public record ProductNoCategory(
                Integer id,
                String name,
                String description,
                Float price,
                Float discount,
                Boolean availability,
                String imageurl,
                List<AddonCategoryNoProduct> addonCategories,
                List<ItemsOnly> items

) {
}