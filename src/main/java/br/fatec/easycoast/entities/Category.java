package br.fatec.easycoast.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Boolean availability;
    
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
    public Boolean getAvailability() {
      return availability;
    }
    public void setAvailability(Boolean availability) {
      this.availability = availability;
    }
  
}
