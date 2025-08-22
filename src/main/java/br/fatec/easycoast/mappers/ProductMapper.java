package br.fatec.easycoast.mappers;

import java.util.List;

import br.fatec.easycoast.dtos.product.ProductAddonCategoryFiltered;
import br.fatec.easycoast.dtos.product.ProductFiltered;
import br.fatec.easycoast.dtos.product.ProductNoCategory;
import br.fatec.easycoast.dtos.product.ProductRequest;
import br.fatec.easycoast.dtos.product.ProductResponse;
import br.fatec.easycoast.entities.Product;

public class ProductMapper {

  public static Product toEntity(ProductRequest request) {
    Product product = new Product();

    product.setName(request.name());
    product.setDescription(request.description());
    product.setPrice(request.price());
    product.setDiscount(request.discount());
    product.setAvailability(request.availability());
    product.setCategory(request.category());
    product.setImageurl(request.imageurl());
    product.setAddonsCategories(request.addonCategories());
    product.setItems(request.items());

    return product;
  }

  // Método e classe utilizado para POST
  public static ProductResponse toDTO(Product product) {

    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getDiscount(),
        product.getAvailability(),
        product.getCategory(),
        product.getImageurl(),
        product.getAddonsCategories() != null ? product.getAddonsCategories() : null,
        product.getItems() != null ? ItemMapper.toListDTO(product.getItems()) : null

    );

  }

  public static ProductAddonCategoryFiltered toDTOFiltered(Product product) {
    return new ProductAddonCategoryFiltered(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getDiscount(),
        product.getAvailability(),
        CategoryMapper.toNoProductsDTO(product.getCategory()),
        product.getImageurl(),
        AddonCategoryMapper.getAddonCategoriesNoProducts(product.getAddonsCategories()),
        ItemMapper.toListItemResponseDTO(product.getItems())

    );

  }

  public static ProductFiltered getProductFiltered(Product product) {
    return new ProductFiltered(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getDiscount(),
        product.getAvailability(),
        product.getCategory() != null ? CategoryMapper.toNoProductsDTO(product.getCategory()) : null,
        product.getImageurl(),
        product.getItems() != null ? ItemMapper.toListItemResponseDTO(product.getItems()) : null);

  }

  public static ProductNoCategory getProductNoCategory(Product product) {
    return new ProductNoCategory(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getDiscount(),
        product.getAvailability(),
        product.getImageurl(),
        AddonCategoryMapper.getAddonCategoriesNoProducts(product.getAddonsCategories()),
        ItemMapper.toListDTO(product.getItems()));

  }

  public static List<ProductNoCategory> getProductNoCategories(List<Product> products) {
    List<ProductNoCategory> productNoCategories = products.stream()
        .map(product -> getProductNoCategory(product))
        .toList();
    return productNoCategories;
  }
}
