package br.fatec.easycoast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
