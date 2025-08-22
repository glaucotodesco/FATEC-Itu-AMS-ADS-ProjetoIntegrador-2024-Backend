package br.fatec.easycoast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.restaurant.RestaurantRequest;
import br.fatec.easycoast.dtos.restaurant.RestaurantResponse;
import br.fatec.easycoast.entities.Restaurant;
import br.fatec.easycoast.mappers.RestaurantMapper;
import br.fatec.easycoast.repositories.RestaurantRepository;
import br.fatec.easycoast.services.exceptions.DatabaseException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RestaurantService {
    @Autowired
    RestaurantRepository restaurantRepository;

    public RestaurantResponse getRestaurant() { // There will be only one restaurant in the DB
        // After catching the restaurant, turn it to a DTO
        return RestaurantMapper.toDto(restaurantRepository.findById(1) // find by 1, because there is only one
                //Needed because the function need
                .orElseThrow(() -> new DatabaseException("The Restaurant hasn't been created yet!")));
    }

    public RestaurantResponse saveRestaurant(RestaurantRequest request) {
        if(restaurantRepository.findById(1).orElse(null) != null){
            throw new DatabaseException("The Restaurant has been created already!");
        }
        Restaurant restaurant = RestaurantMapper.toEntity(request);
        restaurant.setSeats(0);
        restaurant = restaurantRepository.save(restaurant);
        return RestaurantMapper.toDto(restaurant);
    }

    public void updateRestaurant(RestaurantRequest request) {
        try {
            Restaurant restaurant = restaurantRepository.getReferenceById(1);

            restaurant.setName(request.name());
            restaurant.setLocation(request.location());
            restaurant.setWhoAreWe(request.whoAreWe());
            restaurant.setLinks(request.links());
            restaurant.setSchedulings(request.schedulings());

            restaurantRepository.save(restaurant);
        } catch (EntityNotFoundException e) {
            throw new DatabaseException("The Restaurant hasn't been created yet!");
        }
    }

    protected void updateSeats(int quantity) {
        try {
            Restaurant restaurant = restaurantRepository.getReferenceById(1);

            restaurant.setSeats(quantity);

            restaurantRepository.save(restaurant);
        } catch (EntityNotFoundException e) {
            throw new DatabaseException("The Restaurant hasn't been created yet!");
        }
    }
}
