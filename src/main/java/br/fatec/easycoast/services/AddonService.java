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
                .orElseThrow(() -> new EntityNotFoundException("Adicional não encontrado"));
        return AddonMapper.toDTO(addon);
    }

    public AddonResponse saveAddon(AddonRequest request) {
        Addon addon = addonRepository.save(AddonMapper.toEntity(request));
        return AddonMapper.toDTO(addon);
    }

    public void updateAddon(Integer id, AddonRequest request) {
        Addon addon = addonRepository.getReferenceById(id);
        addon.setName(request.name());
        addon.setPrice(request.price());
        addon.setAvailability(request.availability());
        addon.setAddonCategory(request.addonCategory());
        addon.setItem(request.item());

        addonRepository.save(addon);

    }

}
