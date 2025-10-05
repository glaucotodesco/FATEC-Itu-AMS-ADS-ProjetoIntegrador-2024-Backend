package br.fatec.easycoast.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{ 
List<Product> findByNameContainingIgnoreCase(String name); 
}
