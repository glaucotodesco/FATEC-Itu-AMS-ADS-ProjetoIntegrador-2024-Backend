package br.fatec.easycoast.entities;

import java.net.URI;
import java.util.List;

import br.fatec.easycoast.dtos.restaurant.AboutUsSection;
import br.fatec.easycoast.dtos.restaurant.Contact;
import br.fatec.easycoast.dtos.restaurant.Highlight;
import br.fatec.easycoast.dtos.restaurant.Location;
import br.fatec.easycoast.dtos.restaurant.RestaurantScheduling;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_RESTAURANT")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    //This will save the location attributes as tbl_restaurant columns
    @Embedded
    @Column(nullable = false)
    private Location location;

    @ElementCollection
    @CollectionTable(name = "TBL_RESTAURANT_ABOUT_US")
    private List<AboutUsSection> aboutUs;

    //This will save the list as a new table
    @ElementCollection
    @CollectionTable(name = "TBL_RESTAURANT_CONTACT")
    private List<Contact> contacts;

    @ElementCollection
    @CollectionTable(name = "TBL_RESTAURANT_SCHEDULING")
    private List<RestaurantScheduling> schedulings;

    private URI logo;
    private URI banner;
    private List<String> images;

    private Integer seats;

    @ElementCollection
    @CollectionTable(name = "TBL_RESTAURANT_HIGHLIGHT")
    private List<Highlight> highlights;

    public Restaurant() {}

    public Restaurant(Integer id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<AboutUsSection> getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(List<AboutUsSection> aboutUs) {
        this.aboutUs = aboutUs;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<RestaurantScheduling> getSchedulings() {
        return schedulings;
    }

    public void setSchedulings(List<RestaurantScheduling> schedulings) {
        this.schedulings = schedulings;
    }

    public List<Highlight> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<Highlight> highlights) {
        this.highlights = highlights;
    }

    public URI getLogo() {
        return logo;
    }

    public void setLogo(URI logo) {
        this.logo = logo;
    }

    public URI getBanner() {
        return banner;
    }

    public void setBanner(URI banner) {
        this.banner = banner;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
