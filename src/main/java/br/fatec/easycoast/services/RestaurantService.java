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
        if (temp.getLogo() != null) this.removeRestaurantLogo();
        
        //Create a custom name for the logo image
        String newFileName = "restaurantLogo" + "." + file.getContentType().split("/")[1];

        //Save the image
        fileStorageService.store(file, newFileName);
        //Get the URI of the image to show
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/images/{filename}")
                                    .buildAndExpand(newFileName)
                                    .toUri();

        //Set the logo in the restaurant
        temp.setLogo(location);
        restaurantRepository.save(temp);
    }

    public void setRestaurantBanner(MultipartFile file){
        if(!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");

        Restaurant temp = restaurantRepository.getReferenceById(1);
        if (temp.getBanner() != null) this.removeRestaurantBanner();
        
        //Create a custom name for the banner image
        String newFileName = "restaurantBanner" + "." + file.getContentType().split("/")[1];

        //Save the image
        fileStorageService.store(file, newFileName);
        //Get the URI of the image to show
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/images/{filename}")
                                    .buildAndExpand(newFileName)
                                    .toUri();

        //Set the banner in the restaurant
        temp.setBanner(location);
        restaurantRepository.save(temp);
    }

    public synchronized void addRestaurantImage(MultipartFile file){
        if(!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");

        //Get the name of the image
        String filename = file.getOriginalFilename();

        //If there is a file with the same name
        if(fileStorageService.load(filename) != null){
            //Get the type of the file
            String type = file.getContentType().split("/")[1];
            //While there is a file with that name
            for(int i = 1; fileStorageService.load(filename) != null; i++){
                //Add salt in the end of the name, and increase the size of the salt if its necessary
                filename = file.getOriginalFilename().replace("." + type, "") + "-" + RandomStringUtils.randomAlphanumeric(i) + "." + type;
            }
        }

        //Save the image
        fileStorageService.store(file, filename);
        //Get the URI of the image to show
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/images/{filename}")
                                    .buildAndExpand(filename)
                                    .toUri();
        
        //Reload entity to get latest state and avoid overwriting concurrent changes
        Restaurant temp = restaurantRepository.findById(1).orElseThrow(() -> new DatabaseException("The Restaurant hasn't been created yet!"));
        List<String> newImages = new ArrayList<>(temp.getImages());
        //Add the URI to the list
        newImages.add(location.toString());
        //Save it
        temp.setImages(newImages);
        restaurantRepository.save(temp);
    }

    public void removeRestaurantLogo(){
        if(!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");

        Restaurant temp = restaurantRepository.getReferenceById(1);
        if(temp.getLogo() == null) throw new EntityNotFoundException("Restaurant doesn't have a logo!");

        //Get the file name in the URI
        String[] path = temp.getLogo().getPath().split("/");
        //Delete the image
        fileStorageService.deleteFile(path[path.length - 1]);
        //Set the restaurant logo as null
        temp.setLogo(null);
        
        restaurantRepository.save(temp);
    }

    public void removeRestaurantBanner(){
        if(!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");

        Restaurant temp = restaurantRepository.getReferenceById(1);
        if(temp.getBanner() == null) throw new EntityNotFoundException("Restaurant doesn't have a banner!");

        //Get the file name in the URI
        String[] path = temp.getBanner().getPath().split("/");
        //Delete the image
        fileStorageService.deleteFile(path[path.length - 1]);
        //Set the restaurant banner as null
        temp.setBanner(null);
        
        restaurantRepository.save(temp);
    }

    public void removeRestaurantImage(String filename){
        if (!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");
        if (fileStorageService.load(filename) == null) throw new EntityNotFoundException("Couldn't find image: " + filename);

        //Get the URI of the image
        String auxUri = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/images/{filename}")
                        .buildAndExpand(filename)
                        .toUri().toString();

        Restaurant temp = restaurantRepository.getReferenceById(1);
        //Create a new list without the URI of the image
        List<String> newList = temp.getImages().stream().filter(uri -> !uri.equals(auxUri)).toList();
        //Delete the image
        fileStorageService.deleteFile(filename);
        //Set the new List
        temp.setImages(newList);
        //Save
        restaurantRepository.save(temp);
    }
}
