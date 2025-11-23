package br.fatec.easycoast.dtos.order;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.fatec.easycoast.dtos.employee.EmployeeResponse;
import br.fatec.easycoast.dtos.orderItem.OrderItemOrderResponse;
import br.fatec.easycoast.dtos.card.CardResponse;
import br.fatec.easycoast.entities.Seat;

public record OrderOrderResponse(
        Integer id,
        Instant openingTime,
        Instant closingTime,
        Double total,
        CardResponse card,
        Seat seat,
        EmployeeResponse employee,
        @JsonIgnoreProperties("order") List<OrderItemOrderResponse> orderItems
) {
}