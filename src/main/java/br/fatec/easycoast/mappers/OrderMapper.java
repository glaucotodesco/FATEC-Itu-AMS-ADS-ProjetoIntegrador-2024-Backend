package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.order.OrderRequest;
import br.fatec.easycoast.dtos.order.OrderResponse;
import br.fatec.easycoast.entities.Order;

public class OrderMapper {

    public static Order toEntity(OrderRequest request) {
        Order order = new Order();
        order.setOpeningTime(request.openingTime());
        order.setClosingTime(request.closingTime());
        order.setTotal(request.total());
        order.setCard(request.card());
        order.setSeat(request.seat());
        order.setEmployee(request.employee());
        order.setOrderItems(request.orderItems());
        return order;

    }

    public static OrderResponse toDTO(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOpeningTime(),
                order.getClosingTime(),
                order.getTotal(),
                order.getCard(),
                order.getSeat(),
                order.getEmployee(),
                OrderItemMapper.toListDTO(order.getOrderItems()));
    }

}
