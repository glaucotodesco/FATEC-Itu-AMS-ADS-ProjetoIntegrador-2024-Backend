package br.fatec.easycoast.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.addon.AddonNoList;
import br.fatec.easycoast.dtos.addon.AddonRequest;
import br.fatec.easycoast.dtos.addon.AddonResponse;
import br.fatec.easycoast.services.AddonService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@CrossOrigin
@RequestMapping("addons")
public class AddonController {

    @Autowired
    private AddonService addonService;

    // Adicionei AddonNoList par não dar loop, pois o AddonResponse já está sendo
    // utilizado para POST.
    @GetMapping
    public ResponseEntity<List<AddonNoList>> getAddons() {
        return ResponseEntity.ok(addonService.getAddons());
    }

    // A mesma situação do código acima.
    @GetMapping("{id}")
    public ResponseEntity<AddonNoList> getAddonById(@PathVariable Integer id) {
        return ResponseEntity.ok(addonService.getAddonById(id));
    }

    @PostMapping
    public ResponseEntity<AddonResponse> saveAddon(@Valid @RequestBody AddonRequest request) {

        AddonResponse newAddon = addonService.saveAddon(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAddon.id())
                .toUri();

        return ResponseEntity.created(location).body(newAddon);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateAddon(@Valid @PathVariable Integer id,
            @RequestBody AddonRequest request) {
        addonService.updateAddon(id, request);
        ;
        return ResponseEntity.ok().build();

    }

}
