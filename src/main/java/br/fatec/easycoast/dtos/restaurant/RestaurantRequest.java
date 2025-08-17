package br.fatec.easycoast.dtos.restaurant;

import java.util.List;

public record RestaurantRequest(
    String name,
    Location location,
    String whoAreWe,
    List<Link> links
) {

}
