package br.fatec.easycoast.dtos.addonCategory;

import java.util.List;

import br.fatec.easycoast.dtos.addon.AddonFiltered;
import br.fatec.easycoast.dtos.product.ProductFiltered;

// ProductFiltered é uma classe que não tem o AddonCategory, porque é redundante, e vai dar loop. 
public record AddonCategoryResponse(
                Integer id,
                String name,
                AddonType type,
                ProductFiltered product,
                List<AddonFiltered> addons) {

}
