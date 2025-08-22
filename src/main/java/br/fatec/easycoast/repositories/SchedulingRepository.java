package br.fatec.easycoast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.Scheduling;

public interface SchedulingRepository extends JpaRepository<Scheduling, Integer> {
    
}