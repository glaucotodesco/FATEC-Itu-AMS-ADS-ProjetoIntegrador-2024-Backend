package br.fatec.easycoast.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.AddonRequest;
import br.fatec.easycoast.dtos.AddonResponse;
import br.fatec.easycoast.services.AddonService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@CrossOrigin
@RequestMapping("addons")
public class AddonController {

    @Autowired
    private AddonService addonService;

    @GetMapping
    public ResponseEntity <List<AddonResponse>> getAddons() {
        return ResponseEntity.ok(addonService.getAddons());
    }

    @GetMapping("{id}")
    public ResponseEntity <AddonResponse> getAddonById(@PathVariable Integer id) {
        return ResponseEntity.ok(addonService.getAddonById(id));
    }

    @PostMapping
    public ResponseEntity <AddonResponse> save(@Valid @RequestBody AddonRequest request) {
        AddonResponse newAddon = addonService.save(request); 
        URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(newAddon.id())
                            .toUri(); 

        return ResponseEntity.created(location).body(newAddon); 
    }
    
}
