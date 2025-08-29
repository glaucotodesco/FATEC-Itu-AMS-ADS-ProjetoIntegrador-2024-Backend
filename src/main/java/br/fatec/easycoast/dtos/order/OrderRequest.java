package br.fatec.easycoast.dtos.order;

import java.time.Instant;
import java.util.List;

import br.fatec.easycoast.entities.Card;
import br.fatec.easycoast.entities.Employee;
import br.fatec.easycoast.entities.OrderItem;
import br.fatec.easycoast.entities.Seat;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(
        @NotNull(message = "Order opening time can't be null")
        Instant openingTime,
        Instant closingTime,
        @NotNull(message = "Order card can't be null")
        Card card,
        @NotNull(message = "Order seat can't be null")
        Seat seat,
        @NotNull(message = "Order employee can't be null")
        Employee employee,
        List<OrderItem> orderItems

) {
}