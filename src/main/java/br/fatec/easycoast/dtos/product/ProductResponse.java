package br.fatec.easycoast.dtos.product;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.fatec.easycoast.dtos.addonCategory.AddonCategoryResponse;
import br.fatec.easycoast.dtos.subcategory.SubcategoryResponse;

public record ProductResponse(
        Integer id,
        String name,
        String description,
        Double price,
        Double discount,
        Boolean availability,
        @JsonIgnoreProperties("products") SubcategoryResponse subcategory,
        URI image,
        @JsonIgnoreProperties("product") List<AddonCategoryResponse> addonCategories,
        List<ProductItem> items

) {
}
