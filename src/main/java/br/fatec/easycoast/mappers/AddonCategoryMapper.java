package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.addonCategory.AddonCategoryRefDTO;
import br.fatec.easycoast.dtos.addonCategory.AddonCategoryRequest;
import br.fatec.easycoast.dtos.addonCategory.AddonCategoryResponse;
import br.fatec.easycoast.entities.AddonCategory;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                addonCategory.getProduct() != null ? ProductMapper.toProductRefDTO(addonCategory.getProduct()) : null,
                addonCategory.getAddons() != null
                        ? addonCategory.getAddons().stream().map(AddonMapper::toDTO).collect(Collectors.toList())
                        : Collections.emptyList() // <-- MUDANÇA AQUI
        );
    }

    public static AddonCategoryRefDTO toAddonCategoryRefDTO(AddonCategory addonCategory) {
        if (addonCategory == null) {
            return null;
        }
        return new AddonCategoryRefDTO(addonCategory.getId(), addonCategory.getName());
    }

    public static List<AddonCategoryResponse> toListDTO(List<AddonCategory> categories) {
        if (categories == null) {
            return Collections.emptyList();
        }
        return categories.stream().map(AddonCategoryMapper::toDTO).collect(Collectors.toList());
    }
}