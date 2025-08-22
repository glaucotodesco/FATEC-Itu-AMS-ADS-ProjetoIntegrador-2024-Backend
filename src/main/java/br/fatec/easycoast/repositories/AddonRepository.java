package br.fatec.easycoast.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.fatec.easycoast.entities.Addon;

public interface AddonRepository extends JpaRepository<Addon, Integer> {

    @Query("SELECT COUNT(a) FROM Addon a WHERE a.id IN :addonIds AND a.addonCategory.product.id != :productId")
    int findAddonIfexists(@Param("addonIds") List<Integer> addonIds, @Param("productId") Integer productId);

}