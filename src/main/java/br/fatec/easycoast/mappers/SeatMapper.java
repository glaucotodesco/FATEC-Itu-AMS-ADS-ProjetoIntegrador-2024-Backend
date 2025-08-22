package br.fatec.easycoast.mappers;

import java.util.Optional;

import br.fatec.easycoast.dtos.seat.SeatRequest;
import br.fatec.easycoast.dtos.seat.SeatResponse;
import br.fatec.easycoast.entities.Seat;

public class SeatMapper {

    public static Seat toEntity(SeatRequest request) {
        Seat seat = new Seat();
        seat.setStatus(request.status());
        return seat;
    }

    public static SeatResponse toDTO(Seat seat) {
        return new SeatResponse(seat.getId(), seat.getStatus());
    }

    public static SeatResponse toDTO(Optional<Seat> seat) {
        return new SeatResponse(seat.get().getId(), seat.get().getStatus());
    }

}
