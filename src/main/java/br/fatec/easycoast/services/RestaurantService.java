package br.fatec.easycoast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.restaurant.RestaurantRequest;
import br.fatec.easycoast.dtos.restaurant.RestaurantResponse;
import br.fatec.easycoast.entities.Restaurant;
import br.fatec.easycoast.mappers.RestaurantMapper;
import br.fatec.easycoast.repositories.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RestaurantService {
    @Autowired
    RestaurantRepository restaurantRepository;

    public RestaurantResponse getRestaurant() { // There will be only one restaurant in the DB
        // After catching the restaurant, turn it to a DTO
        return RestaurantMapper.toDto(restaurantRepository.findById(1) // find by 1, because there is only one
                .orElseThrow(() -> new EntityNotFoundException("The Restaurant hasn't been created yet!"))); // Needed
                                                                                                             // because
                                                                                                             // the
                                                                                                             // function
                                                                                                             // need
    }

    public RestaurantResponse saveRestaurant(RestaurantRequest request) {
        try {
            return getRestaurant();
        } catch (EntityNotFoundException e) {
            Restaurant restaurant = restaurantRepository.save(RestaurantMapper.toEntity(request));
            return RestaurantMapper.toDto(restaurant);
        }
    }

    public void updateRestaurant(RestaurantRequest request) {
        try {
            Restaurant restaurant = restaurantRepository.getReferenceById(1);

            restaurant.setName(request.name());
            restaurant.setLocation(request.location());
            restaurant.setWhoAreWe(request.whoAreWe());

            restaurantRepository.save(restaurant);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("The Restaurant hasn't been created yet!");
        }
    }

    protected void updateSeats(int quantity) {
        try {
            Restaurant restaurant = restaurantRepository.getReferenceById(1);

            restaurant.setSeats(quantity);

            restaurantRepository.save(restaurant);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("The Restaurant hasn't been created yet!");
        }
    }
}
