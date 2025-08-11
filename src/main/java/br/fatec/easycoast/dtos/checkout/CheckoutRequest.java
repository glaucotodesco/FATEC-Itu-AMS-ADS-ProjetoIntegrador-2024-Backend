package br.fatec.easycoast.dtos.checkout;

import java.time.Instant;

public record CheckoutRequest(
    Instant openingDate,
    Instant closingDate,
    double entryAmount,
    double exitAmount,
    Integer employeeId
) { }
