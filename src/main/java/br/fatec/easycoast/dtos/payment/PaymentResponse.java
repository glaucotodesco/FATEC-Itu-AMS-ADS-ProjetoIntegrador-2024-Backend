package br.fatec.easycoast.dtos.payment;

import java.time.Instant;
import br.fatec.easycoast.entities.Order;

public record PaymentResponse(
    Integer id,
    Double value,
    PaymentMethod methodPayment,
    Instant date,
    PaymentStatus status,
    Order order 
) {}
