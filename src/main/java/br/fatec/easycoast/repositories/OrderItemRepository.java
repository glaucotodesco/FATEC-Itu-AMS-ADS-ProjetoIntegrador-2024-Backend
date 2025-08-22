package br.fatec.easycoast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}