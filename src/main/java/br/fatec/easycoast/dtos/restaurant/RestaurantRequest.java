package br.fatec.easycoast.dtos.restaurant;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record RestaurantRequest(
    @NotBlank(message = "Restaurant name can't be null")
    String name,
    Location location,
    String whoAreWe,
    List<Link> links,
    List<RestaurantScheduling> schedulings
) {

}
