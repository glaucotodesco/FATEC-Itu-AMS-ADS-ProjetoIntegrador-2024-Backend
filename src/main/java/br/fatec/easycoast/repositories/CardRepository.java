package br.fatec.easycoast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {

}
