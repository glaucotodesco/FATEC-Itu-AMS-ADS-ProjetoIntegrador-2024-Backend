package br.fatec.easycoast.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.addon.AddonRequest;
import br.fatec.easycoast.dtos.addon.AddonResponse;
import br.fatec.easycoast.entities.Addon;
import br.fatec.easycoast.mappers.AddonMapper;
import br.fatec.easycoast.repositories.AddonRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AddonService {

    @Autowired
    private AddonRepository addonRepository;

    // Adicionei AddonNoList par não dar loop, pois o AddonResponse já está sendo
    // utilizado para POST.
    public List<AddonResponse> getAddons() {
        List<Addon> addons = addonRepository.findAll();
        return addons.stream()
                .map(addon -> AddonMapper.toDTO(addon))
                .toList();
    }

    // A mesma situação do código acima.
    public AddonResponse getAddonById(Integer id) {
        Addon addon = addonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Addon not found!"));
        return AddonMapper.toDTO(addon);
    }

    public AddonResponse saveAddon(AddonRequest request) {
        Addon addon = AddonMapper.toEntity(request);
        if (addon.getMaxQuantity() == null) addon.setMaxQuantity(1);
        if (addon.getMaxQuantity() != null
        && addon.getMaxQuantity() < 1) throw new IllegalArgumentException("Max quantity must be at least 1");
        addon = addonRepository.save(addon);
        return AddonMapper.toDTO(addon);
    }

    public void updateAddon(Integer id, AddonRequest request) {
        if(!addonRepository.existsById(id)) throw new EntityNotFoundException("Addon not found!");

        Addon addon = addonRepository.getReferenceById(id);
        addon.setName(request.name());
        addon.setPrice(request.price());
        addon.setAvailability(request.availability());
        addon.setAddonCategory(request.addonCategory());
        if (addon.getMaxQuantity() == null) addon.setMaxQuantity(1);
        else addon.setMaxQuantity(request.maxQuantity());
        if (addon.getMaxQuantity() != null
        && addon.getMaxQuantity() < 1) throw new IllegalArgumentException("Max quantity must be at least 1");
        // addon.setItem(request.item());
        addon.setSquare(request.square());

        addonRepository.save(addon);

    }

    public void deleteAddon(int id) {
        if (addonRepository.existsById(id)) {
            addonRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Addon not found!");
        }
    }

}
