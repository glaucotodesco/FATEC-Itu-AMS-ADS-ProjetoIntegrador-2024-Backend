package br.fatec.easycoast.dtos.customer;

import java.time.Instant;

public record CustomerRequest(
        String name,
        String phone,
        Instant birthDate,
        String email) {
}