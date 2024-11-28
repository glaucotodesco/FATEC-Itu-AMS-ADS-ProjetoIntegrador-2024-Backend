package br.fatec.easycoast.resources;

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

  @GetMapping("product/{id}")
  public ResponseEntity<ProductResponse> getProductById (@PathVariable int id) {
    return ResponseEntity.ok(service.getProductById(id));
  }

}
