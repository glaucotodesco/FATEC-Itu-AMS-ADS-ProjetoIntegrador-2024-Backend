package br.fatec.easycoast.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.AddonCategory;

public interface AddonCategoryRepository extends JpaRepository<AddonCategory, Integer> {
    List<AddonCategory> findByProductId(Integer id);

}
