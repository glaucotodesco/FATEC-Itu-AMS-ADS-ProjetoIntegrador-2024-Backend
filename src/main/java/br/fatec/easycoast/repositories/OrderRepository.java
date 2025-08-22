package br.fatec.easycoast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.fatec.easycoast.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
   
}
