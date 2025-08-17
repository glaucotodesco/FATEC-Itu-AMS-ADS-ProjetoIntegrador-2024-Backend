package br.fatec.easycoast.dtos.restaurant;

import java.time.LocalTime;

import jakarta.persistence.Embeddable;

@Embeddable
public class RestaurantScheduling {
    private String name;
    private LocalTime startingTime;
    private LocalTime endingTime;
    //This will need to have 7 values
    private boolean[] availableDays;
    
    public RestaurantScheduling() {}

    public RestaurantScheduling(String name, LocalTime openingTime, LocalTime closingTime,
            boolean[] availableDays) {
        this.name = name;
        this.startingTime = openingTime;
        this.endingTime = closingTime;
        this.availableDays = availableDays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalTime openingTime) {
        this.startingTime = openingTime;
    }

    public LocalTime getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(LocalTime closingTime) {
        this.endingTime = closingTime;
    }

    public boolean[] getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(boolean[] availableDays) {
        this.availableDays = availableDays;
    }
}
