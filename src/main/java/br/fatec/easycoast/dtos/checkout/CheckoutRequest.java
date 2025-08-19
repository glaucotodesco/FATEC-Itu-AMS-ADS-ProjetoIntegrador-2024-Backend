package br.fatec.easycoast.dtos.checkout;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;

public record CheckoutRequest(
    @NotNull(message = "Opening date can't be null")
    Instant openingDate,
    Instant closingDate,
    @NotNull(message = "Entry amount can't be null")
    double entryAmount,
    @NotNull(message = "Exit amount can't be null")
    double exitAmount,
    @NotNull(message = "employee ID can't be null")
    Integer employeeId
) { }
