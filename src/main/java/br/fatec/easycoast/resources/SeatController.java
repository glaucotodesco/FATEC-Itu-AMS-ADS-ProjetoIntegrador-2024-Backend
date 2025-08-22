package br.fatec.easycoast.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fatec.easycoast.dtos.seat.SeatRequest;
import br.fatec.easycoast.dtos.seat.SeatResponse;

import br.fatec.easycoast.services.SeatService;

@RestController
@CrossOrigin
@RequestMapping("seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping
    public ResponseEntity<List<SeatResponse>> getSeats() {
        return ResponseEntity.ok(seatService.getSeats());
    }

    @GetMapping("{id}")
    public ResponseEntity<SeatResponse> getSeat(@PathVariable Integer id) {
        return ResponseEntity.ok(seatService.getSeat(id));

    }

    @PostMapping
    public ResponseEntity<SeatResponse> saveSeat(@RequestBody SeatRequest seatRequest) {
        seatService.saveSeat(seatRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<SeatResponse> updateSeat(@PathVariable Integer id, @RequestBody SeatRequest seatRequest) {
        seatService.updateSeat(id, seatRequest);
        return ResponseEntity.ok().build();
    }

}
