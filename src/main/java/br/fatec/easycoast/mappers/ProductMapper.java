package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.ProductRequest;
import br.fatec.easycoast.dtos.ProductResponse;
import br.fatec.easycoast.entities.Product;

public class ProductMapper {

  public static Product toEntity (ProductRequest request) {
    Product product = new Product();

    product.setName(request.name());
    product.setDescription(request.description());
    product.setPrice(request.price());
    product.setDiscount(request.discount());
    product.setAvailability(request.availability());
    product.setCategory(request.category());

    return product;
  }

  public static ProductResponse toDTO (Product product) {
    ProductResponse response = new ProductResponse(
      product.getId(),
      product.getName(),
      product.getDescription(),
      product.getPrice(),
      product.getDiscount(),
      product.getAvailability(),
      product.getCategory()
    );

    return response;
  }
}
