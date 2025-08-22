package br.fatec.easycoast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}