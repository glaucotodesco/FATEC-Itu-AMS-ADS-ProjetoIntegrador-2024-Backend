package br.fatec.easycoast.mappers;

import java.util.List;

import br.fatec.easycoast.dtos.addon.AddonRequest;
import br.fatec.easycoast.dtos.addon.AddonResponse;
import br.fatec.easycoast.entities.Addon;
import br.fatec.easycoast.entities.AddonCategory;

public class AddonMapper {

    public static Addon toEntity(AddonRequest request) {
        Addon addon = new Addon();
        addon.setName(request.name());
        addon.setPrice(request.price());
        addon.setAvailability(request.availability());
        addon.setItem(request.item());
        addon.setAddonCategory(request.addonCategory());
        return addon;
    }

    public static AddonResponse toDTO(Addon addon) {

        return new AddonResponse(
                addon.getId(),
                addon.getName(),
                addon.getPrice(),
                addon.getAvailability(),
                addon.getItem() != null ? ItemMapper.toDTO(addon.getItem()) : null,
                addon.getAddonCategory() != null ? AddonCategoryMapper.toDTO(addon.getAddonCategory()) : null

        );

    }

    public static List<AddonResponse> toListDTO(List<Addon> addons, Boolean OrderItemResponse) {
        List<AddonResponse> addonResponses = addons.stream()
                .map(addon -> {
                    if (Boolean.TRUE.equals(OrderItemResponse)) {
                        return toDTO(addon);
                    }

                else {
                        return toDTO(
                                new Addon(addon.getId(), addon.getName(), addon.getPrice(), addon.getAvailability(),
                                        addon.getItem(),
                                        new AddonCategory(addon.getAddonCategory().getId(),
                                                addon.getAddonCategory().getName(),
                                                addon.getAddonCategory().getType())));

                    }

                })

                .toList();
        return addonResponses;
    }

    public static List<AddonResponse> toListDTO(List<Addon> addons) {
        return toListDTO(addons, null);
    }

}
