package br.fatec.easycoast.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import br.fatec.easycoast.dtos.ItemResponse;
import br.fatec.easycoast.dtos.SquareFilter;
import jakarta.persistence.Column;

@Entity
@Table(name = "TBL_ITEM")
public class Item {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "SQUARE_ID")
    private Square square;

    public Item(){}

    public Item(Integer id, String name){
        this.id = id;
        this.name = name;
    }
    
    // public Item(Integer id, String name, Square square) {
    //     this.id = id;
    //     this.name = name;
    //     this.square = square;
    // }

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

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public SquareFilter getSquareFilter() {
        SquareFilter squareFilter = new SquareFilter(square.getId(), square.getName());
        return squareFilter;
    }

    // public Square getSquareId() {
    //     Square square2 = new Square(square.getId(), square.getName());
    //     return square2;
    // }


}