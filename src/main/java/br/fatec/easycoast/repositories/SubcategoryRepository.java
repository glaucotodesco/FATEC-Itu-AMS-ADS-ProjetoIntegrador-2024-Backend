package br.fatec.easycoast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

}
