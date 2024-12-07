package br.fatec.easycoast.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.CategoryResponse;
import br.fatec.easycoast.entities.Category;
import br.fatec.easycoast.mappers.CategoryMapper;
import br.fatec.easycoast.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import br.fatec.easycoast.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository repository;

  public List<CategoryResponse> getCategories() {
    return repository.findAll()
        .stream()
        .map(category -> CategoryMapper.toDto(category))
        .toList();
  }

  public CategoryResponse getCategoryById(int id) {
    Category category = repository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("Categoria não Cadastrada"));
    return CategoryMapper.toDto(category);
  }

  public CategoryResponse createCategory(Category category) {
    Category savedCategory = repository.save(category);
    return CategoryMapper.toDto(savedCategory);
  }

  public CategoryResponse updateCategory(int id, Category category) {
    Category existingCategory = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));

    existingCategory.setName(category.getName());
    existingCategory.setAvailability(category.isAvailability());

    Category updatedCategory = repository.save(existingCategory);

    return CategoryMapper.toDto(updatedCategory);
  }

  public void deleteCategory(int id) {
    Category category = repository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("Categoria não encontrada"));
    repository.delete(category);
  }

}
