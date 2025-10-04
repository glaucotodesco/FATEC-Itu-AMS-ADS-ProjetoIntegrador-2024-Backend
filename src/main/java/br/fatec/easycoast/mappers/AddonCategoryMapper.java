package br.fatec.easycoast.mappers;

import java.util.Collections;

import br.fatec.easycoast.dtos.addonCategory.AddonCategoryRequest;
import br.fatec.easycoast.dtos.addonCategory.AddonCategoryResponse;
import br.fatec.easycoast.entities.AddonCategory;

public class AddonCategoryMapper {

    public static AddonCategory toEntity(AddonCategoryRequest request) {
        AddonCategory addonCategory = new AddonCategory();
        addonCategory.setName(request.name());
        addonCategory.setType(request.type());
        addonCategory.setProduct(request.product());
        return addonCategory;
    }

    public static AddonCategoryResponse toDTO(AddonCategory addonCategory) {
        if (addonCategory == null)
            return null;

        return new AddonCategoryResponse(
                addonCategory.getId(),
                addonCategory.getName(),
                addonCategory.getType(),
                addonCategory.getProduct() != null ? ProductMapper.toDTO(addonCategory.getProduct()) : null,
                Collections.emptyList());
    }
}
