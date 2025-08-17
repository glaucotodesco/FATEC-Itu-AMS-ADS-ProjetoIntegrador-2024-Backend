package br.fatec.easycoast.dtos.restaurant;

public record RestaurantResponse(
    Integer id,
    String name,
    Location location,
    String whoAreWe,
    Integer seats
) {

}
