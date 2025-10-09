package br.fatec.easycoast.dtos.restaurant;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public class Location {
    @NotBlank(message = "Zip Code can't be blank")
    @Size(min = 9, max = 9, message = "Zip code needs to follow the pattern 12345-678")
    private String zipCode;
    @NotBlank(message = "Neighborhood can't be blank")
    private String neighborhood;
    @NotBlank(message = "Address can't be blank")
    private String address;
    @NotBlank(message = "Number can't be blank")
    private String number;
    @NotBlank(message = "State can't be blank")
    private String state;
    @NotBlank(message = "City can't be blank")
    private String city;
    
    public Location() {}

    public Location(String zipCode, String neighborhood, String address, String number, String state, String city) {
        this.zipCode = zipCode;
        this.neighborhood = neighborhood;
        this.address = address;
        this.number = number;
        this.state = state;
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
