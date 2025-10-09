package br.fatec.easycoast.mappers;

import java.util.Optional;

import br.fatec.easycoast.dtos.seat.SeatRequest;
import br.fatec.easycoast.dtos.seat.SeatResponse;
import br.fatec.easycoast.entities.Seat;
import jakarta.persistence.EntityNotFoundException;

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
        if(seat.isEmpty()){
            throw new EntityNotFoundException("Seat not found!");
        }
        return new SeatResponse(seat.get().getId(), seat.get().getStatus());
    }

}
