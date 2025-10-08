package br.fatec.easycoast.resources;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.category.CategoryRequest;
import br.fatec.easycoast.dtos.category.CategoryResponse;
import br.fatec.easycoast.services.CategoryService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping 
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(service.getCategories());
    }

    @GetMapping("{id}") 
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable int id) {
        return ResponseEntity.ok(service.getCategory(id));
    }

    @PostMapping 
    public ResponseEntity<CategoryResponse> save(@Valid @RequestBody CategoryRequest category) {
        CategoryResponse newCategory = service.save(category);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCategory.id())
                .toUri();

        return ResponseEntity.created(location).body(newCategory);
    }

    @DeleteMapping("{id}") 
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        service.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateCategory(@Valid @PathVariable int id,
            @RequestBody CategoryRequest category) {
        service.updateCategory(id, category);

        return ResponseEntity.ok().build();
    }
}
