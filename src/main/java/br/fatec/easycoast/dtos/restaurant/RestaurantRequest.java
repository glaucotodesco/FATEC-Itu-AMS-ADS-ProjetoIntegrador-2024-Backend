package br.fatec.easycoast.dtos.restaurant;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record RestaurantRequest(
    @NotBlank(message = "Restaurant name can't be null")
    String name,
    //It can be null, be if it'll have the object, it'll need to be valid
    @Valid
    Location location,
    String whoAreWe,
    @Valid
    List<Link> links,
    @Valid
    List<RestaurantScheduling> schedulings
) {

}
