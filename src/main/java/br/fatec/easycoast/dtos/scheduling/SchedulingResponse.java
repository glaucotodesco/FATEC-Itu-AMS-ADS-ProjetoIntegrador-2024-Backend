package br.fatec.easycoast.dtos.scheduling;

import java.sql.Timestamp;

public record SchedulingResponse(
    Integer id,
    Timestamp startsAt,
    Integer quantity,
    String customerName
) {}
