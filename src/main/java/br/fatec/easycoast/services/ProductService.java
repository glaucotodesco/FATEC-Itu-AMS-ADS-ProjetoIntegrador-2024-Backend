package br.fatec.easycoast.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.product.ProductRequest;
import br.fatec.easycoast.dtos.product.ProductResponse;
import br.fatec.easycoast.entities.Product;
import br.fatec.easycoast.mappers.ProductMapper;
import br.fatec.easycoast.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  public ProductResponse getProductById(int id) {
    return ProductMapper
        .toDTO(productRepository.findById(id).orElseThrow(() -> (new EntityNotFoundException("Product not found"))));
  }

  public List<ProductResponse> getProducts() {
    return productRepository.findAll().stream().map(item -> ProductMapper.toDTO(item)).toList();
  }

  public ProductResponse saveProduct(ProductRequest request) {
    return ProductMapper.toDTO(productRepository.save(ProductMapper.toEntity(request)));
  }

  public void updateProduct(int id, ProductRequest request) {
    Product temp = productRepository.getReferenceById(id);

    temp.setName(request.name());
    temp.setDescription(request.description());
    temp.setPrice(request.price());
    temp.setDiscount(request.discount());
    temp.setAvailability(request.availability());
    temp.setSubcategory(request.subcategory());
    temp.setAddonsCategories(request.addonCategories());
    temp.setItems(request.items());

    productRepository.save(temp);

  }

  public void deleteProduct(int id) {
    if (productRepository.existsById(id)) {
      productRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException("Product not found");
    }
  }
}
