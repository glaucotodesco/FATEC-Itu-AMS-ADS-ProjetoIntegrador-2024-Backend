package br.fatec.easycoast.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PRODUCT")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String description;
  private Float price;
  private Float discount;
  private Boolean availability;

  @ManyToOne
  @JoinColumn(name = "CATEGORY_ID")
  @JsonIgnoreProperties("products")
  @JsonProperty("category")
  private Category category;

  private String imageurl;

  @JsonIgnoreProperties("product")
  @OneToMany(mappedBy = "product")
  private List<AddonCategory> addonsCategories;

  @OneToMany
  @JoinColumn(name = "ITEM_ID")
  private List<Item> items;

  public Product() {
  }

  public Product(Integer id, String name, Float price, Float discount, Boolean availability, Category category,
      String imageurl) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.discount = discount;
    this.availability = availability;
    this.category = category;
    this.imageurl = imageurl;
  }

  public Product(Integer id, String name, Float price, Float discount, Boolean availability, Category category,
      String imageurl, List<AddonCategory> addonCategories, List<Item> items) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.discount = discount;
    this.availability = availability;
    this.category = category;
    this.imageurl = imageurl;
    this.addonsCategories = addonCategories;
    this.items = items;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public Float getDiscount() {
    return discount;
  }

  public void setDiscount(Float discount) {
    this.discount = discount;
  }

  public Boolean getAvailability() {
    return availability;
  }

  public void setAvailability(Boolean availability) {
    this.availability = availability;
  }

  public String getImageurl() {
    return imageurl;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setImageurl(String imageurl) {
    this.imageurl = imageurl;
  }

  public List<AddonCategory> getAddonsCategories() {
    return addonsCategories;
  }

  public void setAddonsCategories(List<AddonCategory> addonsCategories) {
    this.addonsCategories = addonsCategories;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

}
