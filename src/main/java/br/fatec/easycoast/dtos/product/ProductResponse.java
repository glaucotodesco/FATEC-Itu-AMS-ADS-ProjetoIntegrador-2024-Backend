package br.fatec.easycoast.dtos.product;

import java.util.List;

import br.fatec.easycoast.dtos.item.ItemsOnly;
import br.fatec.easycoast.entities.AddonCategory;
import br.fatec.easycoast.entities.Category;

//AddonCategoryFiltered é uma classe que não tem o Product porque é redundante e vai dar loop com ela. 
public record ProductResponse(
                Integer id,
                String name,
                String description,
                Float price,
                Float discount,
                Boolean availability,
                Category category,
                String imageurl,
                List<AddonCategory> addonCategories,
                List<ItemsOnly> items

) {
}
