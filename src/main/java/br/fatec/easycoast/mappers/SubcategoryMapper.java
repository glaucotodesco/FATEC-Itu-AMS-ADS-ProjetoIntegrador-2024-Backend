package br.fatec.easycoast.mappers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.fatec.easycoast.dtos.product.ProductResponse;
import br.fatec.easycoast.dtos.subcategory.SubcategoryRequest;
import br.fatec.easycoast.dtos.subcategory.SubcategoryResponse;
import br.fatec.easycoast.entities.Subcategory;

public class SubcategoryMapper {

  public static Subcategory toEntity(SubcategoryRequest request) {
    if (request == null)
      return null;

    Subcategory subcategory = new Subcategory();
    subcategory.setName(request.name());
    subcategory.setAvailability(request.availability());
    subcategory.setCategory(request.category());
    return subcategory;
  }

  public static SubcategoryResponse toDto(Subcategory subcategory) {
    if (subcategory == null)
      return null;

    List<ProductResponse> productResponses = subcategory.getProducts() != null
        ? subcategory.getProducts().stream()
            .map(ProductMapper::toDTO) // retorna ProductResponse
            .collect(Collectors.toList())
        : Collections.emptyList();

    return new SubcategoryResponse(
        subcategory.getId(),
        subcategory.getName(),
        subcategory.getAvailability(),
        subcategory.getCategory() != null ? CategoryMapper.toDtoShallow(subcategory.getCategory()) : null,
        productResponses);
  }

  public static SubcategoryResponse toDtoShallow(Subcategory subcategory) {
    if (subcategory == null)
      return null;

    return new SubcategoryResponse(
        subcategory.getId(),
        subcategory.getName(),
        subcategory.getAvailability(),
        subcategory.getCategory() != null ? CategoryMapper.toDtoShallow(subcategory.getCategory()) : null,
        Collections.emptyList() // quebra o loop com lista vazia
    );
  }
}
