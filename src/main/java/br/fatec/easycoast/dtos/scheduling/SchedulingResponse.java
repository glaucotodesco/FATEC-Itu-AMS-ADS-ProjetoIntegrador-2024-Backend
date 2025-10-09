package br.fatec.easycoast.dtos.scheduling;

import java.time.Instant;

import br.fatec.easycoast.entities.Customer;
import br.fatec.easycoast.entities.Seat;

public record SchedulingResponse(
        Integer id,
        Instant startsAt,
        Integer quantity,
        Customer customer,
        Seat seat) {
}
