package br.fatec.easycoast.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.addonCategory.AddonCategoryResponse;
import br.fatec.easycoast.dtos.product.ProductRequest;
import br.fatec.easycoast.dtos.product.ProductResponse;
import br.fatec.easycoast.services.AddonCategoryService;
import br.fatec.easycoast.services.ProductService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @Autowired
  private AddonCategoryService addonCategoryService;

  @GetMapping("{id}")
  public ResponseEntity<ProductResponse> getProductById(@PathVariable int id) {
    return ResponseEntity.ok(productService.getProductById(id));
  }

  @GetMapping("{id}/addonCategories")
  public ResponseEntity<List<AddonCategoryResponse>> getAddonCategoriesWithProductId(@PathVariable int id) {
    return ResponseEntity.ok(addonCategoryService.getAddonCategoriesByProductId(id));
  }

  @GetMapping
  public ResponseEntity<List<ProductResponse>> getProducts() {
    return ResponseEntity.ok(productService.getProducts());
  }

  @PostMapping
  public ResponseEntity<ProductResponse> saveProduct(@Valid @RequestBody ProductRequest request) {
    ProductResponse product = productService.saveProduct(request);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(product.id())
        .toUri();

    return ResponseEntity.created(location).body(product);
  }

  @PutMapping("{id}")
  public ResponseEntity<ProductResponse> updateProduct(@Valid @PathVariable int id,
      @RequestBody ProductRequest request) {
    productService.updateProduct(id, request);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

}
