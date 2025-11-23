package br.fatec.easycoast.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.fatec.easycoast.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByCardIdAndClosingTimeIsNull(Integer cardId);
   
    List<Order> findBySeatIdAndClosingTimeIsNull(Integer seatId);
}