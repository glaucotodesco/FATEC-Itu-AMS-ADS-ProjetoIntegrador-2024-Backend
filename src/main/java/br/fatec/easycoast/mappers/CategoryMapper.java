package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.CategoryRequest;
import br.fatec.easycoast.dtos.CategoryResponse;
import br.fatec.easycoast.entities.Category;

public class CategoryMapper {
  
  public static Category toEntity(CategoryRequest request){
    Category category = new Category();
    category.setName(request.name());
    category.setAvailability(request.availability());
    return category;
  }

  public static CategoryResponse toDto(Category category){
    return new CategoryResponse(category.getId(), category.getName(), category.isAvailability());
  }
}
