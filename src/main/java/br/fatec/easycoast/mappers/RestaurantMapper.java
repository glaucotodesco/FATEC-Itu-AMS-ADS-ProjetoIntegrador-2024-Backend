package br.fatec.easycoast.mappers;

import java.util.List;
import java.util.stream.Collectors;

import br.fatec.easycoast.dtos.restaurant.AboutUsSection;
import br.fatec.easycoast.dtos.restaurant.AboutUsSectionRequest;
import br.fatec.easycoast.dtos.restaurant.Highlight;
import br.fatec.easycoast.dtos.restaurant.HighlightRequest;
import br.fatec.easycoast.dtos.restaurant.RestaurantRequest;
import br.fatec.easycoast.dtos.restaurant.RestaurantResponse;
import br.fatec.easycoast.entities.Restaurant;

public class RestaurantMapper {
    public static Restaurant toEntity(RestaurantRequest request) {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.name());
        restaurant.setLocation(request.location());
        restaurant.setAboutUs(mapAboutUsSections(request.aboutUs()));
        restaurant.setContacts(request.contacts());
        restaurant.setSchedulings(request.schedulings());
        restaurant.setHighlights(mapHighlights(request.highlights()));

        return restaurant;
    }

    public static List<AboutUsSection> mapAboutUsSections(List<AboutUsSectionRequest> requests) {
        return requests.stream()
            .map(request -> new AboutUsSection(request.header(), request.content(), null))
            .collect(Collectors.toList());
    }

    public static List<Highlight> mapHighlights(List<HighlightRequest> requests) {
        return requests.stream()
            .map(request -> new Highlight(request.header(), request.content(), null))
            .collect(Collectors.toList());
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
