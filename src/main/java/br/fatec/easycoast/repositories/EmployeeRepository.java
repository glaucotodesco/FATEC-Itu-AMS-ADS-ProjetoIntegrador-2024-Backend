package br.fatec.easycoast.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.fatec.easycoast.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e WHERE e.login = :login AND e.profile != 5")
    Optional<Employee> findByLogin(@Param("login") String login);
    
    @Query("SELECT e FROM Employee e WHERE e.profile != 4")
    List<Employee> findAll();

    @Query("SELECT e FROM Employee e WHERE e.id = :id AND e.profile != 4")
    Optional<Employee> findById(@Param("id") Integer id);

    @Query("SELECT e FROM Employee e WHERE e.profile = 4")
    Optional<Employee> findOwner();

    @Modifying
    @Query("DELETE FROM Employee e WHERE e.profile = 4")
    void deleteOwner();
}