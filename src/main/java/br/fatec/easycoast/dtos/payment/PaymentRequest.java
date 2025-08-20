package br.fatec.easycoast.dtos.payment;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
    @NotNull(message = "The Payment value can't be null")
    Double paymentValue,
    @NotNull(message = "The Payment method can't be null")
    PaymentMethod methodPayment,
    @NotNull(message = "The Payment date can't be null")
    Instant date,
    @NotNull(message = "The Payment status can't be null")
    PaymentStatus status,
    @NotNull(message = "The Payment order ID can't be null")
    Integer orderId
) {}

