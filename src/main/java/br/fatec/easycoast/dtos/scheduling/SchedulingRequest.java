package br.fatec.easycoast.dtos.scheduling;

import java.sql.Timestamp;

public record SchedulingRequest(
    Timestamp startsAt,
    Integer quantity,
    Integer seatId,     
    Integer customerId
) {}
