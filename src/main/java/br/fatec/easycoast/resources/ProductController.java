package br.fatec.easycoast.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.ProductRequest;
import br.fatec.easycoast.dtos.ProductResponse;
import br.fatec.easycoast.services.ProductService;


@RestController
public class ProductController {

  @Autowired
  private ProductService service;

  @GetMapping("products/{id}")
  public ResponseEntity<ProductResponse> getProductById (@PathVariable int id) {
    return ResponseEntity.ok(service.getProductById(id));
  }

  @GetMapping("products")
  public ResponseEntity<List<ProductResponse>> getProducts() {
    return ResponseEntity.ok(service.getProducts());
  }

  @PostMapping("products")
  public ResponseEntity<ProductResponse> postProduct (@RequestBody ProductRequest request) {
    ProductResponse product = this.service.postProduct(request);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.id()).toUri();

    return ResponseEntity.created(location).body(product);
  }

}
