package br.fatec.easycoast.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.subcategory.SubcategoryRequest;
import br.fatec.easycoast.dtos.subcategory.SubcategoryResponse;
import br.fatec.easycoast.services.SubcategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
@RequestMapping("subcategories")
public class SubcategoryController {
    @Autowired
    private SubcategoryService subcategoryService;

    @GetMapping
    public ResponseEntity<List<SubcategoryResponse>> getSubcategories() {
        return ResponseEntity.ok(subcategoryService.getSubcategories());
    }

    @GetMapping("{id}")
    public ResponseEntity<SubcategoryResponse> getSubcategory(@PathVariable Integer id) {
        return ResponseEntity.ok(subcategoryService.getSubcategory(id));

    }

    @PostMapping
    public ResponseEntity<SubcategoryResponse> saveSubcategory(@RequestBody SubcategoryRequest subcategoryRequest) {
        SubcategoryResponse subcategoryResponse = subcategoryService.saveSubcategory(subcategoryRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(subcategoryResponse.id())
                .toUri();
        return ResponseEntity.created(location).body(subcategoryResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateSubcategory(@RequestBody SubcategoryRequest subcategoryRequest,
            @PathVariable Integer id) {
        subcategoryService.updateSubcategory(subcategoryRequest, id);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable int id) {
        subcategoryService.deleteSubcategory(id);

        return ResponseEntity.noContent().build();
    }

}
