package br.fatec.easycoast.dtos.restaurant;

import java.net.URI;
import java.util.List;

public record RestaurantResponse(
    Integer id,
    String name,
    Location location,
    List<AboutUsSection> aboutUs,
    List<Contact> contacts,
    List<RestaurantScheduling> schedulings,
    Integer seats,
    List<Highlight> highlights,
    URI logo,
    URI banner,
    List<String> images
) {

}
