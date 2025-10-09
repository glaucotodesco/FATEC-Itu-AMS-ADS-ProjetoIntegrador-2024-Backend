package br.fatec.easycoast.repositories;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.easycoast.entities.Scheduling;

public interface SchedulingRepository extends JpaRepository<Scheduling, Integer> {

    Optional<Scheduling> findByStartsAtAndSeatId(Instant startsAt, Integer seatId);

    Optional<Scheduling> findByCustomerIdAndStartsAt(Integer customerId, Instant startsAt);

    List<Scheduling> findByCustomerId(Integer customerId);

}