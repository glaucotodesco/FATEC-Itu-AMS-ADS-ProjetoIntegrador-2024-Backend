package br.fatec.easycoast.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.restaurant.RestaurantRequest;
import br.fatec.easycoast.dtos.restaurant.RestaurantResponse;
import br.fatec.easycoast.services.RestaurantService;

@RestController
@CrossOrigin
@RequestMapping("restaurant")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<RestaurantResponse> getRestaurant(){
        return ResponseEntity.ok(restaurantService.getRestaurant());
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> saveRestaurant(@RequestBody RestaurantRequest request) {
        RestaurantResponse restaurant = restaurantService.saveRestaurant(request);

        URI location = ServletUriComponentsBuilder
                       .fromCurrentRequest()
                       .build()
                       .toUri();

        return ResponseEntity.created(location).body(restaurant);
    }

    @PutMapping
    public ResponseEntity<Void> updateRestaurant(@RequestBody RestaurantRequest request){
        restaurantService.updateRestaurant(request);
        return ResponseEntity.ok().build();
    }
}
