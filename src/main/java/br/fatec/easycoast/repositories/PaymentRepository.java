package br.fatec.easycoast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}