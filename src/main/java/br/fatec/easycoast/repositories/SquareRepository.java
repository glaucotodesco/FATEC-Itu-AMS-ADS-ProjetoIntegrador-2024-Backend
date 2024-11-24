package br.fatec.easycoast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.Square;

public interface SquareRepository extends JpaRepository<Square, Integer> {

}
