package br.fatec.easycoast.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

}
