package br.fatec.easycoast.dtos.orderItem;

import java.util.List;

import br.fatec.easycoast.entities.Item;
import br.fatec.easycoast.entities.Order;
import br.fatec.easycoast.entities.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(
        @NotNull(message = "Order Item quantity can't be null")
        Integer quantity,
        String observations,
        @NotNull(message = "Order Item product can't be null")
        Product product,
        @Valid
        List<OrderItemAddon> addons,
        List<Item> removable,
        @NotNull(message = "Order Item order can't be null")
        Order order,
        Boolean reversed
) {

}