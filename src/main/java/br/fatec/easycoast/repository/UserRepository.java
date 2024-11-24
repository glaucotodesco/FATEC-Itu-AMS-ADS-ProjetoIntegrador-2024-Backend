package br.fatec.easycoast.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}