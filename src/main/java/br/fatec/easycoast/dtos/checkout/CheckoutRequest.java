package br.fatec.easycoast.dtos.checkout;

import java.time.Instant;

import br.fatec.easycoast.entities.Employee;
import jakarta.validation.constraints.NotNull;

public record CheckoutRequest(
    @NotNull(message = "Opening date can't be null")
    Instant openingDate,
    Instant closingDate,
    @NotNull(message = "Entry amount can't be null")
    Double entryAmount,
    Double exitAmount,
    Double changeAmount,
    @NotNull(message = "Employee can't be null")
    Employee employee
) { }
