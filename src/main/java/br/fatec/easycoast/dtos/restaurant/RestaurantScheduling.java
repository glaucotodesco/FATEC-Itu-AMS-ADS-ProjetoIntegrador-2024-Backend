package br.fatec.easycoast.dtos.restaurant;

import java.time.LocalTime;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Embeddable
public class RestaurantScheduling {
    @NotBlank(message = "Scheduling name can't be blank")
    private String name;
    @NotNull(message = "Starting Time can't be null")
    private LocalTime startingTime;
    @NotNull(message = "Ending Time can't be null")
    private LocalTime endingTime;
    //This will need to have 7 values
    @NotNull(message = "Available days can't be null")
    @Size(min = 7, max = 7, message = "Available needs to have exactly 7 booleans values, each one representing a day of the week, starting from Sunday and ending with Saturday")
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
