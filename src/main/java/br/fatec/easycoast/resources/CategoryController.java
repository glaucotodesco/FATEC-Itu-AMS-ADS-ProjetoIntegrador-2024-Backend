package br.fatec.easycoast.resources;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin; // Importado
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; // Importado
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.category.CategoryRequest;
import br.fatec.easycoast.dtos.category.CategoryResponse;
import br.fatec.easycoast.services.CategoryService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin // <-- ADICIONADO AQUI
@RequestMapping("categories") // <-- ADICIONADO AQUI
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping // <-- Caminho base já está na classe
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(service.getCategories());
    }

    @GetMapping("{id}") // <-- Caminho relativo
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable int id) {
        return ResponseEntity.ok(service.getCategory(id));
    }

    @PostMapping // <-- Caminho base já está na classe
    public ResponseEntity<CategoryResponse> save(@Valid @RequestBody CategoryRequest category) {
        CategoryResponse newCategory = service.save(category);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCategory.id())
                .toUri();

        return ResponseEntity.created(location).body(newCategory);
    }

    @DeleteMapping("{id}") // <-- Caminho relativo
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
