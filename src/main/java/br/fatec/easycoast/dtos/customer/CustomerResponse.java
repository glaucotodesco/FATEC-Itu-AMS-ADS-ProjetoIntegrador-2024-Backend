package br.fatec.easycoast.dtos.customer;

import java.time.Instant;

public record CustomerResponse(
        Integer id,
        String name,
        String phone,
        Instant birthDate,
        String email

) {

}
