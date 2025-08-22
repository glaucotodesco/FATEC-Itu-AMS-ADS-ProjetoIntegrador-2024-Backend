package br.fatec.easycoast.mappers;

import java.util.List;

import br.fatec.easycoast.dtos.addonCategory.AddonCategoryFiltered;
import br.fatec.easycoast.dtos.addonCategory.AddonCategoryNoList;
import br.fatec.easycoast.dtos.addonCategory.AddonCategoryNoProduct;
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

    public static AddonCategoryResponse toDTO(AddonCategory addonCategory) {

        return new AddonCategoryResponse(
                addonCategory.getId(),
                addonCategory.getName(),
                addonCategory.getType(),
                addonCategory.getProduct() != null ? ProductMapper.getProductFiltered(addonCategory.getProduct())
                        : null,
                addonCategory.getAddons() != null ? AddonMapper.getAddonFiltered(addonCategory.getAddons()) : null

        );

    }

    public static AddonCategoryFiltered toGetDTO(AddonCategory addonCategory) {
        return new AddonCategoryFiltered(
                addonCategory.getId(),
                addonCategory.getName(),
                addonCategory.getType(),
                addonCategory.getProduct() != null ? addonCategory.getProductFiltered() : null,
                addonCategory.getAddons() != null ? AddonMapper.getAddonFiltered(addonCategory.getAddons()) : null

        );

    }

    public static AddonCategoryNoProduct getAddonCategoryNoProduct(AddonCategory addonCategory) {
        return new AddonCategoryNoProduct(
                addonCategory.getId(), addonCategory.getName(), addonCategory.getType(),
                AddonMapper.getAddonFiltered(addonCategory.getAddons()));

    }

    // Retornar lista sem produto para POST
    public static List<AddonCategoryNoProduct> getAddonCategoriesNoProducts(List<AddonCategory> addonCategories) {
        List<AddonCategoryNoProduct> addonCategoriesNoProducts = addonCategories.stream()
                .map(addonCategory -> new AddonCategoryNoProduct(addonCategory.getId(), addonCategory.getName(),
                        addonCategory.getType(), AddonMapper.getAddonFiltered(addonCategory.getAddons())))
                .toList();
        return addonCategoriesNoProducts;

    }

    public static AddonCategoryNoList getAddonCategoryNoList(AddonCategory addonCategory) {
        return new AddonCategoryNoList(
                addonCategory.getId(),
                addonCategory.getName(),
                addonCategory.getType(),
                addonCategory.getProduct() != null ? addonCategory.getProductFiltered() : null);

    }

}
