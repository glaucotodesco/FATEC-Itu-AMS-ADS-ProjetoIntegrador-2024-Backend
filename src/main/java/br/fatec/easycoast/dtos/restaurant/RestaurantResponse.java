package br.fatec.easycoast.dtos.restaurant;

import java.util.List;

public record RestaurantResponse(
    Integer id,
    String name,
    Location location,
    String whoAreWe,
    List<Contact> links,
    List<RestaurantScheduling> schedulings,
    Integer seats
) {

}
