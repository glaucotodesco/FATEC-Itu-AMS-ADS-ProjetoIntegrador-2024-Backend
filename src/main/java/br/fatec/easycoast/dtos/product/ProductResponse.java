package br.fatec.easycoast.dtos.product;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.fatec.easycoast.dtos.subcategory.SubcategoryResponse;
import br.fatec.easycoast.entities.AddonCategory;
import br.fatec.easycoast.entities.Item;

public record ProductResponse(
        Integer id,
        String name,
        String description,
        Double price,
        Double discount,
        Boolean availability,
        @JsonIgnoreProperties("products") SubcategoryResponse subcategory,
        URI image,
        @JsonIgnoreProperties("product") List<AddonCategory> addonCategories,
        List<Item> items

) {
}
