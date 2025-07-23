package br.fatec.easycoast.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.seat.SeatRequest;
import br.fatec.easycoast.dtos.seat.SeatResponse;
import br.fatec.easycoast.dtos.seat.SeatStatus;
import br.fatec.easycoast.entities.Seat;
import br.fatec.easycoast.mappers.SeatMapper;
import br.fatec.easycoast.repositories.SeatRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private RestaurantService restaurantService;

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

    public List<SeatResponse> manageSeat(int start, int end) {
        List<SeatResponse> seats = new ArrayList<SeatResponse>();

        if (start <= end && end > 0) {
            SeatResponse aux = null;

            int last = new LinkedList<SeatResponse>(getSeats()).getLast().id();
            int i = last >= start ? start : last + 1;
            for (; i <= end; i++) {
                if (i > 0) {
                    try {
                        seats.add(this.getSeat(i));
                    } catch (EntityNotFoundException e) {
                        aux = this.saveSeat(new SeatRequest(SeatStatus.FREE));
                        if (aux.id() >= start) {
                            seats.add(aux);
                        }
                    }
                }
            }

            restaurantService.updateSeats(end);
        }

        return seats;
    }

    public SeatResponse updateSeat(Integer id, SeatRequest request) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assento não encontrado com ID: " + id));

        // Atualiza os campos do assento
        seat.setStatus(request.status());

        return SeatMapper.toDTO(seatRepository.save(seat));
    }
}
