package br.fatec.easycoast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}