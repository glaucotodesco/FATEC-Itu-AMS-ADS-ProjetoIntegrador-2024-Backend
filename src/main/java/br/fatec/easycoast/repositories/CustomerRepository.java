package br.fatec.easycoast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}