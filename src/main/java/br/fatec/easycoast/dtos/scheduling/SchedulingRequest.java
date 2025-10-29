package br.fatec.easycoast.dtos.scheduling;

import java.time.Instant;

import br.fatec.easycoast.entities.Customer;
import br.fatec.easycoast.entities.Seat;
import jakarta.validation.constraints.NotNull;

public record SchedulingRequest(
        @NotNull(message = "The Scheduling start can't be null")
        Instant startsAt,
        @NotNull(message = "The Scheduling quantity can't be null")
        Integer quantity,
        @NotNull(message = "The Scheduling seat can't be null")
        Seat seat,
        String observations,
        @NotNull(message = "The Scheduling customer can't be null")
        Customer customer
) {
}
