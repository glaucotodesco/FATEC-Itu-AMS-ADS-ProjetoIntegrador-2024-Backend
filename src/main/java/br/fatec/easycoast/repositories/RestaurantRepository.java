package br.fatec.easycoast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

}
