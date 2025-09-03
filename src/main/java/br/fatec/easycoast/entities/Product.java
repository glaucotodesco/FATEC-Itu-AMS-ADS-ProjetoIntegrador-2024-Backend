package br.fatec.easycoast.entities;

import java.util.List;

import org.hibernate.annotations.SoftDelete;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PRODUCT")
@SoftDelete
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String description;
  private Double price;
  private Double discount;
  private Boolean availability;

  @ManyToOne
  @JoinColumn(name = "SUBCATEGORY_ID")
  @JsonIgnoreProperties("products")

  private Subcategory subcategory;

  @Lob
  private byte[] image;

  @JsonIgnoreProperties("product")
  @OneToMany(mappedBy = "product")
  private List<AddonCategory> addonsCategories;

  @OneToMany
  @JoinColumn(name = "ITEM_ID")
  private List<Item> items;

  public Product() {
  }

  public Product(Integer id, String name, Double price, Double discount, Boolean availability, Subcategory subcategory,
      byte[] image) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.discount = discount;
    this.availability = availability;
    this.subcategory = subcategory;
    this.image = image;
  }

  public Product(Integer id, String name, Double price, Double discount, Boolean availability, Subcategory subcategory,
      byte[] image, List<AddonCategory> addonCategories, List<Item> items) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.discount = discount;
    this.availability = availability;
    this.subcategory = subcategory;
    this.image = image;
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

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Double getDiscount() {
    return discount;
  }

  public void setDiscount(Double discount) {
    this.discount = discount;
  }

  public Boolean getAvailability() {
    return availability;
  }

  public void setAvailability(Boolean availability) {
    this.availability = availability;
  }

  public byte[] getImage() {
    return image;
  }

  public Subcategory getSubcategory() {
    return subcategory;
  }

  public void setSubcategory(Subcategory subcategory) {
    this.subcategory = subcategory;
  }

  public void setImage(byte[] image) {
    this.image = image;
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
