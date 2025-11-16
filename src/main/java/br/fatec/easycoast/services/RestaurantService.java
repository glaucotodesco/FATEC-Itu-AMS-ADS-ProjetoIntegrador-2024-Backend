package br.fatec.easycoast.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.restaurant.RestaurantRequest;
import br.fatec.easycoast.dtos.restaurant.RestaurantResponse;
import br.fatec.easycoast.entities.Restaurant;
import br.fatec.easycoast.dtos.restaurant.AboutUsSection;
import br.fatec.easycoast.dtos.restaurant.Highlight;
import br.fatec.easycoast.mappers.RestaurantMapper;
import br.fatec.easycoast.repositories.RestaurantRepository;
import br.fatec.easycoast.services.enums.Folder;
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
        try{
            if(restaurantRepository.findById(1).orElse(null) != null){
                throw new DatabaseException("The Restaurant has been created already!");
            }
            Restaurant restaurant = RestaurantMapper.toEntity(request);
            restaurant.setSeats(0);
            restaurant.setImages(new ArrayList<String>());
            restaurant = restaurantRepository.save(restaurant);
            return RestaurantMapper.toDto(restaurant);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("The header of a about-us section, or of a highlight, must be unique!");
        }
    }

    public synchronized void updateRestaurant(RestaurantRequest request) {
        if(!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");
        try {
            Restaurant restaurant = restaurantRepository.getReferenceById(1);

            restaurant.setName(request.name());
            restaurant.setLocation(request.location());
            updateAboutUsSections(restaurant, RestaurantMapper.mapAboutUsSections(request.aboutUs()));
            restaurant.setContacts(request.contacts());
            restaurant.setSchedulings(request.schedulings());
            updateHighlights(restaurant, RestaurantMapper.mapHighlights(request.highlights()));

            restaurantRepository.save(restaurant);
        } catch (EntityNotFoundException e) {
            throw new DatabaseException(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("The header of a about-us section, or of a highlight, must be unique!");
        }
    }

    private synchronized void updateAboutUsSections(Restaurant restaurant, List<AboutUsSection> newSections) {
        List<AboutUsSection> oldSections = restaurant.getAboutUs();
        
        // Update existing sections and preserve images
        for (AboutUsSection newSection : newSections) {
            AboutUsSection existingSection = oldSections.stream()
                .filter(old -> old.getHeader().equals(newSection.getHeader()))
                .findFirst().orElse(null);
            
            if (existingSection != null) {
                newSection.setImage(existingSection.getImage());
            }
        }
        
        // Delete images of removed sections
        for (AboutUsSection oldSection : oldSections) {
            boolean sectionStillExists = newSections.stream()
                .anyMatch(newSec -> newSec.getHeader().equals(oldSection.getHeader()));
            
            if (!sectionStillExists && oldSection.getImage() != null) {
                String[] path = oldSection.getImage().getPath().split("/");
                fileStorageService.deleteFile(path[path.length - 1], Folder.RESTAURANT_ABOUT_US);
            }
        }
        
        restaurant.setAboutUs(newSections);
    }

    private synchronized void updateHighlights(Restaurant restaurant, List<Highlight> newHighlights) {
        List<Highlight> oldHighlights = restaurant.getHighlights();
        
        // Update existing highlights and preserve images
        for (Highlight newHighlight : newHighlights) {
            Highlight existingHighlight = oldHighlights.stream()
                .filter(old -> old.getHeader().equals(newHighlight.getHeader()))
                .findFirst().orElse(null);
            
            if (existingHighlight != null) {
                newHighlight.setImage(existingHighlight.getImage());
            }
        }
        
        // Delete images of removed highlights
        for (Highlight oldHighlight : oldHighlights) {
            boolean highlightStillExists = newHighlights.stream()
                .anyMatch(newHigh -> newHigh.getHeader().equals(oldHighlight.getHeader()));
            
            if (!highlightStillExists && oldHighlight.getImage() != null) {
                String[] path = oldHighlight.getImage().getPath().split("/");
                fileStorageService.deleteFile(path[path.length - 1], Folder.RESTAURANT_HIGHLIGHTS);
            }
        }
        
        restaurant.setHighlights(newHighlights);
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

    public synchronized void setRestaurantLogo(MultipartFile file){
        if(!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");

        Restaurant temp = restaurantRepository.findById(1).orElseThrow(() -> new DatabaseException("The Restaurant hasn't been created yet!"));
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

        //Reload entity to get latest state
        temp = restaurantRepository.findById(1).orElseThrow(() -> new DatabaseException("The Restaurant hasn't been created yet!"));
        //Set the logo in the restaurant
        temp.setLogo(location);
        restaurantRepository.save(temp);
    }

    public synchronized void setRestaurantBanner(MultipartFile file){
        if(!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");

        Restaurant temp = restaurantRepository.findById(1).orElseThrow(() -> new DatabaseException("The Restaurant hasn't been created yet!"));
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

        //Reload entity to get latest state
        temp = restaurantRepository.findById(1).orElseThrow(() -> new DatabaseException("The Restaurant hasn't been created yet!"));
        //Set the banner in the restaurant
        temp.setBanner(location);
        restaurantRepository.save(temp);
    }

    private String generateUniqueFilename(String originalFilename, String contentType, Folder folder) {
        String filename = originalFilename;
        String type = contentType.split("/")[1];
        
        if(fileStorageService.load(filename, folder) != null){
            for(int i = 1; fileStorageService.load(filename, folder) != null; i++){
                filename = originalFilename.replace("." + type, "") + 
                           "-" + RandomStringUtils.randomAlphanumeric(i) + 
                           "." + type;
            }
        }
        return filename;
    }

    public synchronized void addRestaurantImage(MultipartFile file){
        if(!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");

        String filename = generateUniqueFilename(file.getOriginalFilename(), file.getContentType(), Folder.RESTAURANT_IMAGES);

        //Save the image
        fileStorageService.store(file, filename, Folder.RESTAURANT_IMAGES);
        //Get the URI of the image to show
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/images/restaurantImages/{filename}")
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

    public synchronized void setAboutUsSectionImage(String header, MultipartFile file){
        if(!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");
        
        Restaurant temp = restaurantRepository.findById(1)
        .orElseThrow(() -> new DatabaseException("The Restaurant hasn't been created yet!"));
        
        // Check if the section exists
        AboutUsSection section = temp.getAboutUs().stream()
            .filter(s -> s.getHeader()
                          .replace(" ", "+")
                          .equals(header))
            .findFirst().orElseThrow(() -> new EntityNotFoundException("Couldn't find section with header: " + header));
        
        if(section.getImage() != null) this.removeAboutUsSectionImage(header);

        String baseFilename = header + "." + file.getContentType().split("/")[1];
        String filename = generateUniqueFilename(baseFilename, file.getContentType(), Folder.RESTAURANT_ABOUT_US);

        //Save the image
        fileStorageService.store(file, filename, Folder.RESTAURANT_ABOUT_US);
        //Get the URI of the image to show
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/images/restaurantAboutUs/{filename}")
                                    .buildAndExpand(filename)
                                    .toUri();

        temp.getAboutUs().forEach(s -> {
            if(s.getHeader()
                .replace(" ", "+")
                .equals(header))
                s.setImage(location);
        });

        restaurantRepository.save(temp);
    }

    public synchronized void setHighlightImage(String header, MultipartFile file){
        Restaurant temp = restaurantRepository.findById(1)
        .orElseThrow(() -> new DatabaseException("The Restaurant hasn't been created yet!"));
        
        Highlight highlight = temp.getHighlights().stream()
            .filter(h -> h.getHeader()
                          .replace(" ", "+")
                          .equals(header))
            .findFirst().orElseThrow(() -> new EntityNotFoundException("Couldn't find highlight with header: " + header));
        
        if(highlight.getImage() != null) this.removeHighlightImage(header);

        String baseFilename = header + "." + file.getContentType().split("/")[1];
        String filename = generateUniqueFilename(baseFilename, file.getContentType(), Folder.RESTAURANT_HIGHLIGHTS);

        //Save the image
        fileStorageService.store(file, filename, Folder.RESTAURANT_HIGHLIGHTS);
        //Get the URI of the image to show
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/images/restaurantHighlights/{filename}")
                                    .buildAndExpand(filename)
                                    .toUri();

        temp.getHighlights().forEach(h -> {
            if(h.getHeader()
                .replace(" ", "+")
                .equals(header))
                h.setImage(location);
        });

        restaurantRepository.save(temp);
    }

    public synchronized void removeRestaurantLogo(){
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

    public synchronized void removeRestaurantBanner(){
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

    public synchronized void removeRestaurantImage(String filename){
        if (!restaurantRepository.existsById(1)) throw new DatabaseException("The Restaurant hasn't been created yet!");
        if (fileStorageService.load(filename) == null) throw new EntityNotFoundException("Couldn't find image: " + filename);

        //Get the URI of the image
        String auxUri = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/images/{filename}")
                        .buildAndExpand(filename)
                        .toUri().toString();

        Restaurant temp = restaurantRepository.findById(1).orElseThrow(() -> new DatabaseException("The Restaurant hasn't been created yet!"));
        //Create a new list without the URI of the image
        List<String> newList = temp.getImages().stream().filter(uri -> !uri.equals(auxUri)).toList();
        //Delete the image
        fileStorageService.deleteFile(filename);
        //Set the new List
        temp.setImages(newList);
        //Save
        restaurantRepository.save(temp);
    }

    public synchronized void removeAboutUsSectionImage(String header){
        Restaurant temp = restaurantRepository.findById(1)
        .orElseThrow(() -> new DatabaseException("The Restaurant hasn't been created yet!"));
        //Get the section
        AboutUsSection section = temp.getAboutUs().stream()
            .filter(s -> s.getHeader()
                          .replace(" ", "+")
                          .equals(header))
            .findFirst().orElseThrow(() -> new EntityNotFoundException("Couldn't find section with header:" + header));

        //Get the file name in the URI
        String[] path = section.getImage().getPath().split("/");
        //Delete the image
        fileStorageService.deleteFile(path[path.length - 1], Folder.RESTAURANT_ABOUT_US);
        //Set the section image as null
        section.setImage(null);

        restaurantRepository.save(temp);
    }

    public synchronized void removeHighlightImage(String title){
        Restaurant temp = restaurantRepository.findById(1)
        .orElseThrow(() -> new DatabaseException("The Restaurant hasn't been created yet!"));
        
        //Get the highlight
        var highlight = temp.getHighlights().stream()
            .filter(h -> h.getHeader()
                          .replace(" ", "+")
                          .equals(title))
            .findFirst().orElseThrow(() -> new EntityNotFoundException("Couldn't find highlight with title:" + title));

        //Get the file name in the URI
        String[] path = highlight.getImage().getPath().split("/");
        //Delete the image
        fileStorageService.deleteFile(path[path.length - 1], Folder.RESTAURANT_HIGHLIGHTS);
        //Set the highlight image as null
        highlight.setImage(null);

        restaurantRepository.save(temp);
    }
}
