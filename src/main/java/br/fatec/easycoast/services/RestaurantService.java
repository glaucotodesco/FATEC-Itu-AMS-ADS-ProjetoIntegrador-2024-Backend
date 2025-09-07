package br.fatec.easycoast.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FileStorageService fileStorageService;

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
        restaurant.setImages(new ArrayList<String>());
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

    public void setRestaurantLogo(MultipartFile file){
        if(!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");

        Restaurant temp = restaurantRepository.getReferenceById(1);
        //if (temp.getLogo() != null) this.removeRestaurantLogo();
        
        String newFileName = "restaurantLogo" + "." + file.getContentType().split("/")[1];

        fileStorageService.store(file, newFileName);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/images/{filename}")
                                    .buildAndExpand(newFileName)
                                    .toUri();

        temp.setLogo(location);
        restaurantRepository.save(temp);
    }

    public void setRestaurantBanner(MultipartFile file){
        if(!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");

        Restaurant temp = restaurantRepository.getReferenceById(1);
        //if (temp.getBanner() != null) this.removeRestaurantBanner();
        
        String newFileName = "restaurantBanner" + "." + file.getContentType().split("/")[1];

        fileStorageService.store(file, newFileName);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/images/{filename}")
                                    .buildAndExpand(newFileName)
                                    .toUri();

        temp.setBanner(location);
        restaurantRepository.save(temp);
    }

    public void addRestaurantImage(MultipartFile file){
        if(!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");

        Restaurant temp = restaurantRepository.getReferenceById(1);
        String filename = file.getOriginalFilename();

        if(fileStorageService.load(filename) != null){
            String type = file.getContentType().split("/")[1];
            for(int i = 1; fileStorageService.load(filename) != null; i++){
                filename = file.getOriginalFilename().replace("." + type, "") + "-" + RandomStringUtils.randomAlphanumeric(i) + "." + type;
            }
        }

        fileStorageService.store(file, filename);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/images/{filename}")
                                    .buildAndExpand(filename)
                                    .toUri();
        List<String> newImages = temp.getImages();
        newImages.add(location.toString());

        temp.setImages(newImages);
        restaurantRepository.save(temp);
    }

    public void removeRestaurantImage(String filename){
        if (!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");
        if (fileStorageService.load(filename) == null) throw new EntityNotFoundException("Couldn't find image: " + filename);

        String auxUri = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/images/{filename}")
                        .buildAndExpand(filename)
                        .toUri().toString();

        Restaurant temp = restaurantRepository.getReferenceById(1);
        List<String> newList = temp.getImages().stream().filter(uri -> !uri.equals(auxUri)).toList();

        fileStorageService.deleteFile(filename);
        temp.setImages(newList);
        
        restaurantRepository.save(temp);
    }
}
