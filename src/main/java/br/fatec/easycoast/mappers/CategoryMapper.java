package br.fatec.easycoast.mappers;

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

  // Obs: Um Category pode ter vários produtos inseridos.
  public static CategoryResponse toDto(Category category) {
    return new CategoryResponse(
        category.getId(),
        category.getName(),
        category.getAvailability(),
        category.getProducts());
  }

}
