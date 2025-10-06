package br.fatec.easycoast.entities;

import java.util.List;

import org.hibernate.annotations.SoftDelete;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.fatec.easycoast.dtos.product.ProductItem;
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
  @JsonBackReference

  private Subcategory subcategory;

  private String imageurl;

  @JsonIgnoreProperties("product")
  @OneToMany(mappedBy = "product")
  private List<AddonCategory> addonsCategories;

  @JsonIgnore
  @OneToMany
  @JoinColumn(name = "ITEM_ID")
  private List<ProductItem> items;

  public Product() {
  }

  public Product(Integer id, String name, Double price, Double discount, Boolean availability, Subcategory subcategory,
      String imageurl) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.discount = discount;
    this.availability = availability;
    this.subcategory = subcategory;
    this.imageurl = imageurl;
  }

  public Product(Integer id, String name, Double price, Double discount, Boolean availability, Subcategory subcategory,
      String imageurl, List<AddonCategory> addonCategories, List<ProductItem> items) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.discount = discount;
    this.availability = availability;
    this.subcategory = subcategory;
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

  public String getImageurl() {
    return imageurl;
  }

  public Subcategory getSubcategory() {
    return subcategory;
  }

  public void setSubcategory(Subcategory subcategory) {
    this.subcategory = subcategory;
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

  public List<ProductItem> getItems() {
    return items;
  }

  public void setItems(List<ProductItem> items) {
    this.items = items;
  }

}
