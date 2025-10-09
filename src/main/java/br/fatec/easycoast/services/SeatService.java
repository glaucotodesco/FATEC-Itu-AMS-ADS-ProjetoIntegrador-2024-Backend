package br.fatec.easycoast.services;

import java.util.ArrayList;
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
        //The seats that will be on response
        List<SeatResponse> seats = new ArrayList<SeatResponse>();
        //Getting number of seats the restaurant has
        int numberOfSeats = restaurantService.getRestaurant().seats();
        //If the restaurant has seats
        if (numberOfSeats > 0) {
            //Until reach the number of restaurant seats
            for (int i = 1; i <= numberOfSeats; i++) {
                //Add to the list
                seats.add(this.getSeat(i));
            }
        }

        //Return the list
        return seats;
    }

    public List<SeatResponse> filterSeats(int start, int end) {
        if (start < 0) throw new IllegalArgumentException("The 'start' param can't be negative!");
        //The seats that will be on response
        List<SeatResponse> seats = new ArrayList<SeatResponse>();
        //Getting number of seats the restaurant has
        int numberOfSeats = restaurantService.getRestaurant().seats();
        //The first seat id need to be lower than the last seat id
        //And the last need to be higher than 0
        if (start <= end && end > 0) {
            //Until reach the last id or the number of restaurant seats
            for (int i = start; i <= end && i <= numberOfSeats ; i++) {
                if (i > 0) {
                    //Add to the list
                    seats.add(this.getSeat(i));
                }
            }
        } else {
            throw new IllegalArgumentException("The 'end' param needs to be higher than the 'start' param!");
        }

        //Return the list
        return seats;
    }

    public SeatResponse getSeat(Integer id) {
        return SeatMapper.toDTO(seatRepository.findById(id));
    }

    public SeatResponse saveSeat(SeatRequest seat) {
        return SeatMapper.toDTO(seatRepository.save(SeatMapper.toEntity(seat)));
    }

    public List<SeatResponse> manageSeats(int newQuantity) {
        if(newQuantity < 0) throw new IllegalArgumentException("The restaurant can't have negative seats!");
        //The seats that will be on response
        List<SeatResponse> seats = new ArrayList<SeatResponse>();

        if (newQuantity >= 0) {
            //For the seat that may not exist
            SeatResponse aux = null;
            for (int i = 1; i <= newQuantity; i++) {
                //If the seat already exists
                try {
                    //Add to the list
                    seats.add(this.getSeat(i));
                //If the seat doesn't exist yet
                } catch (EntityNotFoundException e) {
                    //Create the sit
                    aux = this.saveSeat(new SeatRequest(SeatStatus.FREE));
                    //add to the list
                    seats.add(aux);
                }
            }

            //Update the number of seats of the restaurant
            restaurantService.updateSeats(newQuantity);
        } else {
            restaurantService.updateSeats(0);
        }

        return seats;
    }

    public SeatResponse updateSeat(Integer id, SeatRequest request) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seat not found by ID: " + id));

        // Atualiza os campos do assento
        seat.setStatus(request.status());

        return SeatMapper.toDTO(seatRepository.save(seat));
    }
}
