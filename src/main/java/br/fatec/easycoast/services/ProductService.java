package br.fatec.easycoast.services;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.product.ProductRequest;
import br.fatec.easycoast.dtos.product.ProductResponse;
import br.fatec.easycoast.entities.Product;
import br.fatec.easycoast.mappers.ProductMapper;
import br.fatec.easycoast.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
  @Autowired
  private FileStorageService fileStorageService;

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
    if (!productRepository.existsById(id)) throw new EntityNotFoundException("Product not found!");

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

  public void setProductImage(int id, MultipartFile file){
    if (!productRepository.existsById(id)) throw new EntityNotFoundException("Product not found!");

    Product temp = productRepository.getReferenceById(id);
    if (temp.getImage() != null) this.removeProductImage(id);
    
    String newFileName = "product" + id + "." + file.getContentType().split("/")[1];

    fileStorageService.store(file, newFileName);
    URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/images/{filename}")
                                .buildAndExpand(newFileName)
                                .toUri();

    temp.setImage(location);
    productRepository.save(temp);
  }

  public void removeProductImage(int id){
    if (!productRepository.existsById(id)) throw new EntityNotFoundException("Product not found!");

    Product temp = productRepository.getReferenceById(id);
    if(temp.getImage() == null) throw new EntityNotFoundException("This product doesn't have a image");

    String[] path = temp.getImage().getPath().split("/");
    fileStorageService.deleteFile(path[path.length - 1]);
    temp.setImage(null);
    
    productRepository.save(temp);
  }

  public void deleteProduct(int id) {
    if (productRepository.existsById(id)) {
      removeProductImage(id);
      productRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException("Product not found!");
    }
  }
}
