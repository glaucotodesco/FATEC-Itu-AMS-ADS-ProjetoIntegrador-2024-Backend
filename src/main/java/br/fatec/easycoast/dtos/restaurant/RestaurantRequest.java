package br.fatec.easycoast.dtos.restaurant;

public record RestaurantRequest(
    String name,
    Location location,
    String whoAreWe
) {

}
