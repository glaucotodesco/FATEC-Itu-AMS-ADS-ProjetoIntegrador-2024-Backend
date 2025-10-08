package br.fatec.easycoast.dtos.product;

import br.fatec.easycoast.entities.Item;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record ProductItem(
    @NotNull(message = "Product Item can't be null")
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    Item item,
    @NotNull(message = "Item removable can't be null")
    Boolean removable
) {

}
