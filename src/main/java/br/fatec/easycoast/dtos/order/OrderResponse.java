package br.fatec.easycoast.dtos.order;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.fatec.easycoast.dtos.orderItem.OrderItemResponse;
import br.fatec.easycoast.entities.Card;
import br.fatec.easycoast.entities.Employee;
import br.fatec.easycoast.entities.Seat;

public record OrderResponse(

        Integer id,
        Instant openingTime,
        Instant closingTime,
        Double total,
        Card card,
        Seat seat,
        Employee employee,
        @JsonIgnoreProperties("order") List<OrderItemResponse> orderItems

) {
}