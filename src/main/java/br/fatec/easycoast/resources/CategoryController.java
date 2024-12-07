package br.fatec.easycoast.resources;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.fatec.easycoast.dtos.CategoryResponse;
import br.fatec.easycoast.entities.Category;
import br.fatec.easycoast.services.CategoryService;

@RestController

public class CategoryController {

  @Autowired
  private CategoryService service;

  @GetMapping("categories")
  public ResponseEntity<List<CategoryResponse>> getCategories() {
    return ResponseEntity.ok(service.getCategories());
  }

  @GetMapping("categories/{id}")
  public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable int id) {
    return ResponseEntity.ok(service.getCategoryById(id));
  }
 @PostMapping("/categories")
  public ResponseEntity<CategoryResponse> createCategory(@RequestBody Category category) {
    CategoryResponse createdCategory = service.createCategory(category);
    return ResponseEntity.status(201).body(createdCategory); 
  }

  @PutMapping("/categories/{id}")
  public ResponseEntity<CategoryResponse> updateCategory(@PathVariable int id, @RequestBody Category category) {
      CategoryResponse updatedCategory = service.updateCategory(id, category);
      return ResponseEntity.ok(updatedCategory);
  }
  
  @DeleteMapping("categories/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
    service.deleteCategory(id);
    return ResponseEntity.noContent().build(); // Retorna o status 204 após a exclusão
  }
  

}
