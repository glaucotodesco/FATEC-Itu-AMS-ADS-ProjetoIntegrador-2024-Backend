package br.fatec.easycoast.mappers;

import java.util.List;

import br.fatec.easycoast.dtos.addon.AddonFiltered;
import br.fatec.easycoast.dtos.addon.AddonNoList;
import br.fatec.easycoast.dtos.addon.AddonRequest;
import br.fatec.easycoast.dtos.addon.AddonResponse;
import br.fatec.easycoast.entities.Addon;

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
                ItemMapper.toDTO(addon.getItem()),
                AddonCategoryMapper.toDTO(addon.getAddonCategory())

        );

    }

    public static List<AddonResponse> toListDTO(List<Addon> addons) {
        List<AddonResponse> addonResponses = addons.stream()
                .map(addon -> toDTO(addon))
                .toList();
        return addonResponses;
    }

    // Método para enviar somente o AddonCategory, e não enviar Addons também para
    // não ficar redudante e dar loop.
    public static AddonNoList toDTONoList(Addon addon) {

        return new AddonNoList(
                addon.getId(),
                addon.getName(),
                addon.getPrice(),
                addon.getAvailability(),
                ItemMapper.toDTO(addon.getItem()),
                addon.getAddonCategory() != null ? AddonCategoryMapper.getAddonCategoryNoList(addon.getAddonCategory())
                        : null);

    }

    // Vai converter List<Addon> em AddonFiltered para não enviar produto para não
    // ficar redundante na hora de pesquisar o produto.
    public static List<AddonFiltered> getAddonFiltered(List<Addon> addons) {
        List<AddonFiltered> addonFiltered = addons.stream()
                .map(a -> a.getAddonFiltered())
                .toList();
        return addonFiltered;

    }

}
