package br.fatec.easycoast.dtos.seat;

import jakarta.validation.constraints.NotNull;

public record SeatRequest(
        @NotNull(message = "Seat status can't be null")
        SeatStatus status
) {
}