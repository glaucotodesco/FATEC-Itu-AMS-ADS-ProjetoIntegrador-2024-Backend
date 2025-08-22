package br.fatec.easycoast.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.seat.SeatRequest;
import br.fatec.easycoast.dtos.seat.SeatResponse;
import br.fatec.easycoast.entities.Seat;
import br.fatec.easycoast.mappers.SeatMapper;
import br.fatec.easycoast.repositories.SeatRepository;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public List<SeatResponse> getSeats() {
        List<SeatResponse> seats = seatRepository.findAll()
                .stream()
                .map(seat -> SeatMapper.toDTO(seat))
                .toList();

        return seats;
    }

    public SeatResponse getSeat(Integer id) {
        return SeatMapper.toDTO(seatRepository.findById(id));
    }

    public SeatResponse saveSeat(SeatRequest seat) {
        return SeatMapper.toDTO(seatRepository.save(SeatMapper.toEntity(seat)));
    }

    public SeatResponse updateSeat(Integer id, SeatRequest request) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assento não encontrado com ID: " + id));

        // Atualiza os campos do assento
        seat.setStatus(request.status());

        return SeatMapper.toDTO(seatRepository.save(seat));
    }
}
