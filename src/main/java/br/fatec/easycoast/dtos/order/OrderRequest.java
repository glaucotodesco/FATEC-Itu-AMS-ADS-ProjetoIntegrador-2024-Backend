package br.fatec.easycoast.dtos.order;

import java.time.Instant;
import java.util.List;

import br.fatec.easycoast.entities.Card;
import br.fatec.easycoast.entities.Employee;
import br.fatec.easycoast.entities.OrderItem;
import br.fatec.easycoast.entities.Seat;

public record OrderRequest(
        Instant openingTime,
        Instant closingTime,
        Double total,
        Card card,
        Seat seat,
        Employee employee,
        List<OrderItem> orderItems

) {
}