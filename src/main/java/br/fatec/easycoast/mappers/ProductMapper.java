package br.fatec.easycoast.mappers;

import java.util.Collections; // Import necessário

import br.fatec.easycoast.dtos.product.ProductRequest;
import br.fatec.easycoast.dtos.product.ProductResponse;
import br.fatec.easycoast.entities.Product;

public class ProductMapper {

  public static Product toEntity(ProductRequest request) {
    Product product = new Product();
    // (O seu código toEntity aqui está correto, não precisa de mudanças)
    product.setName(request.name());
    product.setDescription(request.description());
    product.setPrice(request.price());
    product.setDiscount(request.discount());
    product.setAvailability(request.availability());
    product.setSubcategory(request.subcategory());
    product.setImageurl(request.imageurl());
    // Lógica para addonCategories e items, se necessário
    return product;
  }

  public static ProductResponse toDTO(Product product) {
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getDiscount(),
        product.getAvailability(),
        // MUDANÇA 1: A ordem dos parâmetros foi corrigida para bater com o record
        product.getImageurl(),
        // MUDANÇA 2: Chamando o mapper raso da subcategoria para evitar loop
        product.getSubcategory() != null ? SubcategoryMapper.toDtoShallow(product.getSubcategory()) : null,
        // MUDANÇA 3: Enviando listas vazias conforme a definição do nosso record de teste
        Collections.emptyList(),
        Collections.emptyList()
    );
  }
}