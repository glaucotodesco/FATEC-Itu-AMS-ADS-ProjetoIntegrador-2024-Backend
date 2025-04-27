package br.fatec.easycoast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.Addon;

public interface AddonRepository extends JpaRepository <Addon, Integer> {

    
} 