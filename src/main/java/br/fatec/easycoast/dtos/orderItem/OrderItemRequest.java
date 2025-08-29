package br.fatec.easycoast.dtos.orderItem;

import java.util.List;

import br.fatec.easycoast.entities.Addon;
import br.fatec.easycoast.entities.Order;
import br.fatec.easycoast.entities.Product;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(
        @NotNull(message = "Order Item quantity can't be null")
        Integer quantity,
        String observations,
        @NotNull(message = "Order Item product can't be null")
        Product product,
        List<Addon> addons,
        @NotNull(message = "Order Item order can't be null")
        Order order
) {

}