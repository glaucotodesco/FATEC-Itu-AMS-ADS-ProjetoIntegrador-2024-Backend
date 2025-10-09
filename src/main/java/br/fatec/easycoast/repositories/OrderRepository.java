package br.fatec.easycoast.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.fatec.easycoast.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByCardIdAndClosingTimeIsNull(Integer cardId);
   /**
    * Este método é crucial. O Spring Data JPA o transforma automaticamente
    * em uma consulta SQL (ex: SELECT * FROM TBL_ORDER WHERE card_id = ? LIMIT 1)
    * para encontrar o primeiro pedido associado a uma comanda.
    */
   //Optional<Order> findFirstByCardId(Integer cardId);
   
}