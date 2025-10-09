// Verifique se o seu arquivo está assim: br.fatec.easycoast.dtos.product.ProductResponse.java
package br.fatec.easycoast.dtos.product;

import br.fatec.easycoast.dtos.addonCategory.AddonCategoryResponse;
import br.fatec.easycoast.dtos.item.ItemResponse;
import br.fatec.easycoast.dtos.subcategory.SubcategoryRefDTO;
import java.util.List;

public record ProductResponse(
    Integer id,
    String name,
    String description,
    Double price,
    Double discount,
    Boolean availability,
    String imageurl,
    SubcategoryRefDTO subcategory,
    List<AddonCategoryResponse> addonCategories,
    List<ItemResponse> items) {
}