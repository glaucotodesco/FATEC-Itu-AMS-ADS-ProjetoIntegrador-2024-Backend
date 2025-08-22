package br.fatec.easycoast.dtos.restaurant;

public record RestaurantResponse(
    Integer id,
    String name,
    String location,
    String whoAreWe
) {

}
