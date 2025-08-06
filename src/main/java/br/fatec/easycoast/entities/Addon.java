package br.fatec.easycoast.entities;

import org.hibernate.annotations.SoftDelete;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_ADDON")
@SoftDelete
public class Addon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
    private Float price;

    @Column(nullable = false)
    private Boolean availability;

    @ManyToOne
    @JoinColumn(name = "SQUARE_ID")
    private Square square;

    @ManyToOne
    @JoinColumn(name = "ADDONCATEGORY_ID")
    @JsonIgnoreProperties("addons")
    private AddonCategory addonCategory;

    public Addon() {
    }

    public Addon(Integer id, String name, Float price, Boolean availability, Square square,
            AddonCategory addonCategory) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availability = availability;
        this.square = square;
        this.addonCategory = addonCategory;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public AddonCategory getAddonCategory() {
        return addonCategory;
    }

    public void setAddonCategory(AddonCategory addonCategory) {
        this.addonCategory = addonCategory;
    }

}
