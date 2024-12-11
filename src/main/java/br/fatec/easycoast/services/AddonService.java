package br.fatec.easycoast.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.AddonRequest;
import br.fatec.easycoast.dtos.AddonResponse;
import br.fatec.easycoast.entities.Addon;
import br.fatec.easycoast.mappers.AddonMapper;
import br.fatec.easycoast.repositories.AddonRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AddonService {

    @Autowired
    private AddonRepository addonRepository;

    public List<AddonResponse> getAddons() {
        List<Addon> addons = addonRepository.findAll();
        return addons.stream()
                .map(addon -> AddonMapper.toDTO(addon))
                .toList();
    }

    public AddonResponse getAddonById(Integer id) {
        Addon addon = addonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Adicional não encontrado"));
        return AddonMapper.toDTO(addon);
    }

    public AddonResponse save(AddonRequest request) {
        Addon addon = addonRepository.save(AddonMapper.toEntity(request));
        System.out.println(addon);
        return AddonMapper.toDTO(addon);
    }

}
