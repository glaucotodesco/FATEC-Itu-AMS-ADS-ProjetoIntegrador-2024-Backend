package br.fatec.easycoast.dtos.orderItem;

import java.util.List;

import br.fatec.easycoast.entities.Addon;
import br.fatec.easycoast.entities.Order;
import br.fatec.easycoast.entities.Product;

public record OrderItemRequest(
        Integer quantity,
        String observations,
        Double total,
        Product product,
        List<Addon> addons,
        Order order

) {

}