package br.fatec.easycoast.dtos.product;

import java.util.List;

import br.fatec.easycoast.dtos.addonCategory.AddonCategoryNoProduct;
import br.fatec.easycoast.dtos.category.CategoryNoProducts;
import br.fatec.easycoast.dtos.item.ItemResponse;

public record ProductAddonCategoryFiltered(
        Integer id,
        String name,
        String description,
        Float price,
        Float discount,
        Boolean availability,
        CategoryNoProducts category,
        String imageurl,
        List<AddonCategoryNoProduct> addonCategories,
        List<ItemResponse> items

) {

}
