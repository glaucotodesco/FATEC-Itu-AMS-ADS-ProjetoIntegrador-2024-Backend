package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.restaurant.RestaurantRequest;
import br.fatec.easycoast.dtos.restaurant.RestaurantResponse;
import br.fatec.easycoast.entities.Restaurant;

public class RestaurantMapper {
    public static Restaurant toEntity(RestaurantRequest request) {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.name());
        restaurant.setLocation(request.location());
        restaurant.setAboutUs(request.aboutUs());
        restaurant.setContacts(request.contacts());
        restaurant.setSchedulings(request.schedulings());
        restaurant.setHighlights(request.highlights());

        return restaurant;
    }

    public static RestaurantResponse toDto(Restaurant restaurant) {
        return new RestaurantResponse(restaurant.getId(),
                                      restaurant.getName(),
                                      restaurant.getLocation(),
                                      restaurant.getAboutUs(),
                                      restaurant.getContacts(),
                                      restaurant.getSchedulings(),
                                      restaurant.getSeats(),
                                      restaurant.getHighlights(),
                                      restaurant.getLogo(),
                                      restaurant.getBanner(),
                                      restaurant.getImages()
        );
    }
}
