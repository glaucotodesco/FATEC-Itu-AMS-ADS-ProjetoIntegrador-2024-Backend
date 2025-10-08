package br.fatec.easycoast.dtos.product;

import br.fatec.easycoast.entities.Item;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class ProductItem {
    
    @NotNull(message = "Product Item can't be null")
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;
    
    @NotNull(message = "Item removable can't be null")
    private Boolean removable;
    
    public ProductItem() {}
    
    public ProductItem(Item item, Boolean removable) {
        this.item = item;
        this.removable = removable;
    }
    
    public Item getItem() {
        return item;
    }
    
    public void setItem(Item item) {
        this.item = item;
    }
    
    public Boolean getRemovable() {
        return removable;
    }
    
    public void setRemovable(Boolean removable) {
        this.removable = removable;
    }
}
