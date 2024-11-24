package br.fatec.easycoast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{ }
