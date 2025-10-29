package br.fatec.easycoast.dtos.orderItem;

import br.fatec.easycoast.entities.Addon;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class OrderItemAddon {
    @NotNull(message = "Addon can't be null")
    @ManyToOne
    @JoinColumn(name = "ADDON_ID")
    private Addon addon;
    private Integer quantity;

    public OrderItemAddon(Addon addon, Integer quantity) {
        this.addon = addon;
        this.quantity = quantity;
    }

    public OrderItemAddon() {
    }

    public Addon getAddon() {
        return addon;
    }

    public void setAddon(Addon addon) {
        this.addon = addon;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
