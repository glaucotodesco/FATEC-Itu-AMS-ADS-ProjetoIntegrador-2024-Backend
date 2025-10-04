package br.fatec.easycoast.dtos.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.fatec.easycoast.dtos.addonCategory.AddonCategoryResponse;
import br.fatec.easycoast.dtos.item.ItemResponse;
import br.fatec.easycoast.dtos.subcategory.SubcategoryResponse;

import java.util.List;

public record ProductResponse(
                Integer id,
                String name,
                String description,
                Double price,
                Double discount,
                Boolean availability,
                String imageurl,
                @JsonIgnoreProperties("products") SubcategoryResponse subcategory,
                List<AddonCategoryResponse> addonCategories,
                List<ItemResponse> items) {
}
