package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.addonCategory.AddonCategoryRequest;
import br.fatec.easycoast.dtos.addonCategory.AddonCategoryResponse;
import br.fatec.easycoast.entities.AddonCategory;

public class AddonCategoryMapper {

    public static AddonCategory ToEntity(AddonCategoryRequest request) {
        AddonCategory addonCategory = new AddonCategory();
        addonCategory.setName(request.name());
        addonCategory.setType(request.type());
        addonCategory.setProduct(request.product());
        return addonCategory;
    }

    public static AddonCategoryResponse toDTO(AddonCategory addonCategory, boolean product) {

        return new AddonCategoryResponse(
                addonCategory.getId(),
                addonCategory.getName(),
                addonCategory.getType(),
                addonCategory.getProduct() != null && product ? ProductMapper.toDTO(addonCategory.getProduct()) : null,
                addonCategory.getAddons() != null ? AddonMapper.toListDTO(addonCategory.getAddons()) : null

        );

    }

    public static AddonCategoryResponse toDTO(AddonCategory addonCategory) {
        return toDTO(addonCategory, true);
    }
}
