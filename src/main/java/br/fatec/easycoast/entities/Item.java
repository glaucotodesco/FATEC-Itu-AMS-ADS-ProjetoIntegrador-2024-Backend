package br.fatec.easycoast.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import br.fatec.easycoast.dtos.item.ItemResponse;
import jakarta.persistence.Column;

@Entity
@Table(name = "TBL_ITEM")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "SQUARE_ID", nullable = true)
    private Square square;

    public Item() {
    }

    public Item(ItemResponse itemResponse) {
        this.id = itemResponse.id();
        this.name = itemResponse.name();
        this.square = new Square(itemResponse.square());

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

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    // Aqui o square vai enviar a cópia dele mesmo só que filtrado para
    // SquareResponse.
    public ItemResponse getItemResponse() {
        return new ItemResponse(id, name, square.getSquareResponse());

    }
}
