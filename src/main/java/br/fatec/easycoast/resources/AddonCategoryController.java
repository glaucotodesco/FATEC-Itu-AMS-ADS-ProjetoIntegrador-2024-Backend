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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.addonCategory.AddonCategoryFiltered;
import br.fatec.easycoast.dtos.addonCategory.AddonCategoryRequest;
import br.fatec.easycoast.dtos.addonCategory.AddonCategoryResponse;
import br.fatec.easycoast.services.AddonCategoryService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("addoncategories")
public class AddonCategoryController {

    @Autowired
    private AddonCategoryService addonCategoryService;

    @GetMapping
    public ResponseEntity<List<AddonCategoryFiltered>> getAddonCategories() {
        return ResponseEntity.ok(addonCategoryService.getAddonCategories());
    }

    @GetMapping("{id}")
    public ResponseEntity<AddonCategoryFiltered> getAddonCategory(@PathVariable Integer id) {
        return ResponseEntity.ok(addonCategoryService.getAddonCategory(id));
    }

    @PostMapping
    public ResponseEntity<AddonCategoryResponse> saveAddonCategory(@Valid @RequestBody AddonCategoryRequest request) {

        AddonCategoryResponse addonCategoryResponse = addonCategoryService.saveAddonCategory(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addonCategoryResponse.id())
                .toUri();

        return ResponseEntity.created(location).body(addonCategoryResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateAddonCategory(@Valid @PathVariable Integer id,
            @RequestBody AddonCategoryRequest addonCategory) {
        addonCategoryService.updateAddonCategory(id, addonCategory);
        return ResponseEntity.ok().build();

    }
}
