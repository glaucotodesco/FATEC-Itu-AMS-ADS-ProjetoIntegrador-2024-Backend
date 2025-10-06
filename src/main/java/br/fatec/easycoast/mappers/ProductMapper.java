package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.product.ProductRequest;
import br.fatec.easycoast.dtos.product.ProductResponse;
import br.fatec.easycoast.dtos.product.ProductRefDTO;
import br.fatec.easycoast.entities.Product;
import java.util.Collections;
import java.util.stream.Collectors;

public class ProductMapper {

  public static Product toEntity(ProductRequest request) {
    Product product = new Product();
    product.setName(request.name());
    product.setDescription(request.description());
    product.setPrice(request.price());
    product.setDiscount(request.discount());
    product.setAvailability(request.availability());
    product.setSubcategory(request.subcategory());
    product.setImageurl(request.imageurl());
    return product;
  }

  public static ProductResponse toDTO(Product product) {
    if (product == null)
      return null;

    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getDiscount(),
        product.getAvailability(),
        product.getImageurl(),
        product.getSubcategory() != null ? SubcategoryMapper.toSubcategoryRefDTO(product.getSubcategory()) : null,
        product.getAddonsCategories() != null
            ? product.getAddonsCategories().stream().map(AddonCategoryMapper::toDTO).collect(Collectors.toList())
            : Collections.emptyList(),
        Collections.emptyList());
  }

  // <-- NOVO MÉTODO AQUI -->
  // Este método cria a referência simples para evitar recursão.
  public static ProductRefDTO toProductRefDTO(Product product) {
    if (product == null) {
      return null;
    }
    return new ProductRefDTO(product.getId(), product.getName());
  }
}