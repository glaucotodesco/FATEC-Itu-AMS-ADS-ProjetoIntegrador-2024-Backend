package br.fatec.easycoast.dtos.checkout;

import java.time.Instant;

import br.fatec.easycoast.entities.Employee;

public record CheckoutResponse(
    Integer id,
    Instant openingDate,
    Instant closingDate,
    double entryAmount,
    double exitAmount,
    Employee employee
) { }
