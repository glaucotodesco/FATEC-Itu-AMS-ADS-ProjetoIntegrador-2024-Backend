package br.fatec.easycoast.repositories;

import br.fatec.easycoast.entities.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Integer> {
}

