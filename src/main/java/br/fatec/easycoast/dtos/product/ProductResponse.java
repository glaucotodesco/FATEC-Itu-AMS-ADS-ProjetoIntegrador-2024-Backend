package br.fatec.easycoast.dtos.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.fatec.easycoast.entities.AddonCategory;
import br.fatec.easycoast.entities.Category;
import br.fatec.easycoast.entities.Item;

//AddonCategoryFiltered é uma classe que não tem o Product porque é redundante e vai dar loop com ela. 
public record ProductResponse(
                Integer id,
                String name,
                String description,
                Float price,
                Float discount,
                Boolean availability,
                @JsonIgnoreProperties("products") Category category,
                String imageurl,
                @JsonIgnoreProperties("product") List<AddonCategory> addonCategories,
                List<Item> items

) {
}
