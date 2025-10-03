package br.fatec.easycoast.mappers;

import java.util.Collections;
import java.util.stream.Collectors; // Import necessário

import br.fatec.easycoast.dtos.subcategory.SubcategoryRequest;
import br.fatec.easycoast.dtos.subcategory.SubcategoryResponse;
import br.fatec.easycoast.entities.Subcategory;

public class SubcategoryMapper {

  public static Subcategory toEntity(SubcategoryRequest request) {
    Subcategory subcategory = new Subcategory();
    subcategory.setName(request.name());
    subcategory.setAvailability(request.availability());
    subcategory.setCategory(request.category());
    return subcategory;
  }

  // Mapeamento COMPLETO
  public static SubcategoryResponse toDto(Subcategory subcategory) {
    return new SubcategoryResponse(
        subcategory.getId(),
        subcategory.getName(),
        subcategory.getAvailability(),
        // MUDANÇA: Chamando o mapper raso para a categoria pai para evitar o loop
        CategoryMapper.toDtoShallow(subcategory.getCategory()),
        // MUDANÇA: Convertendo a lista de Entidades de Produto para uma lista de DTOs
        subcategory.getProducts()
            .stream()
            .map(ProductMapper::toDTO)
            .collect(Collectors.toList())
    );
  }

  // Mapeamento RASO: para ser chamado de dentro do ProductMapper
  public static SubcategoryResponse toDtoShallow(Subcategory subcategory) {
    return new SubcategoryResponse(
        subcategory.getId(),
        subcategory.getName(),
        subcategory.getAvailability(),
        // MUDANÇA: Chama o mapper raso para a categoria pai
        CategoryMapper.toDtoShallow(subcategory.getCategory()),
        Collections.emptyList() // MUDANÇA: Retorna lista de produtos vazia para quebrar o loop
    );
  }
}