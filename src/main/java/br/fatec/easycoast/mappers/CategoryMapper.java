package br.fatec.easycoast.mappers;

import java.util.Collections;
import java.util.stream.Collectors; // Import necessário

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

  // Mapeamento COMPLETO: usado quando você quer a categoria com todas as suas
  // subcategorias
  public static CategoryResponse toDto(Category category) {
    // Verificação de nulo para evitar o crash
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

  // Mapeamento RASO (SHALLOW): usado para evitar loops infinitos
  // Quando chamado de dentro do SubcategoryMapper, ele não inclui a lista de
  // subcategorias
  public static CategoryResponse toDtoShallow(Category category) {
    // Verificação de nulo para evitar o crash
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