package br.fatec.easycoast.resources;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.fatec.easycoast.dtos.CategoryResponse;
import br.fatec.easycoast.entities.Category;
import br.fatec.easycoast.services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

  @Autowired
  private CategoryService service;

  // Listar todas as categorias
  @GetMapping
  public ResponseEntity<List<CategoryResponse>> getCategories() {
    return ResponseEntity.ok(service.getCategories());
  }

  // Obter uma categoria por ID
  @GetMapping("/{id}")
  public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable int id) {
    return ResponseEntity.ok(service.getCategoryById(id));
  }

  // Criar uma nova categoria
  @PostMapping
  public ResponseEntity<CategoryResponse> createCategory(@RequestBody Category category) {
    CategoryResponse createdCategory = service.createCategory(category);
    return ResponseEntity.status(201).body(createdCategory);
  }

  // Atualizar uma categoria existente
  @PutMapping("/{id}")
  public ResponseEntity<CategoryResponse> updateCategory(@PathVariable int id, @RequestBody Category category) {
    CategoryResponse updatedCategory = service.updateCategory(id, category);
    return ResponseEntity.ok(updatedCategory);
  }

  // Deletar uma categoria
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
    service.deleteCategory(id);
    return ResponseEntity.noContent().build();
  }
}
