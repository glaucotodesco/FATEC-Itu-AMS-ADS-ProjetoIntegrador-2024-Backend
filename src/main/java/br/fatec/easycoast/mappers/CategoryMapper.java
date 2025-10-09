package br.fatec.easycoast.mappers;

import java.util.Collections;
import java.util.stream.Collectors; 

import br.fatec.easycoast.dtos.category.CategoryRequest;
import br.fatec.easycoast.dtos.category.CategoryResponse;
import br.fatec.easycoast.entities.Category;

public class CategoryMapper {

  public static Category toEntity(CategoryRequest request) {
    Category category = new Category();
    category.setName(request.name());
    category.setAvailability(request.availability());
    return category;
  }

  public static CategoryResponse toDto(Category category) {
    if (category == null) {
      return null;
    }
    return new CategoryResponse(
        category.getId(),
        category.getName(),
        category.getAvailability(),
        category.getSubcategories()
            .stream()
            .map(SubcategoryMapper::toDto)
            .collect(Collectors.toList()));
  }

  
  public static CategoryResponse toDtoShallow(Category category) {
    if (category == null) {
      return null;
    }
    return new CategoryResponse(
        category.getId(),
        category.getName(),
        category.getAvailability(),
        Collections.emptyList());
  }
}