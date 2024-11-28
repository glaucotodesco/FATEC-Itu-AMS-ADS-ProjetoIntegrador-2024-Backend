package br.fatec.easycoast.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.ProductResponse;
import br.fatec.easycoast.mappers.ProductMapper;
import br.fatec.easycoast.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

  @Autowired
  private ProductRepository repository;

  public ProductResponse getProductById (int id) {
    return ProductMapper.toDTO(repository.findById(id).orElseThrow(() -> (
      new EntityNotFoundException("Product not found")
    )));
  }

  public List<ProductResponse> getProducts () {
    return repository.findAll().stream().map(item -> ProductMapper.toDTO(item)).toList();
  }

}
