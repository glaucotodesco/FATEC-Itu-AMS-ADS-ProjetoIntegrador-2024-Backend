package br.fatec.easycoast.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.fatec.easycoast.dtos.square.SquareResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_SQUARES")
public class Square {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties("square")
    @OneToMany(mappedBy = "square")
    private List<Item> items;

    public Square() {
    }

    public Square(SquareResponse squareResponse) {
        this.id = squareResponse.id();
        this.name = squareResponse.name();
    }

    public Square(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Square(Integer id, String name, List<Item> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
