package br.fatec.easycoast.mappers;

import java.util.Collections;
import java.util.List;
import br.fatec.easycoast.dtos.addon.AddonRequest;
import br.fatec.easycoast.dtos.addon.AddonResponse;
import br.fatec.easycoast.entities.Addon;

public class AddonMapper {

    public static Addon toEntity(AddonRequest request) {
        Addon addon = new Addon();
        addon.setName(request.name());
        addon.setPrice(request.price());
        addon.setAvailability(request.availability());
        addon.setSquare(request.square());
        addon.setAddonCategory(request.addonCategory());
        return addon;
    }

    public static AddonResponse toDTO(Addon addon) {
        if (addon == null)
            return null;

        return new AddonResponse(
                addon.getId(),
                addon.getName(),
                addon.getPrice(),
                addon.getAvailability(),
                addon.getSquare() != null ? SquareMapper.toDto(addon.getSquare()) : null,
                addon.getAddonCategory() != null ? AddonCategoryMapper.toAddonCategoryRefDTO(addon.getAddonCategory())
                        : null);
    }

    public static List<AddonResponse> toListDTO(List<Addon> addons) {
        if (addons == null)
            return Collections.emptyList();
        return addons.stream().map(AddonMapper::toDTO).toList();
    }
}