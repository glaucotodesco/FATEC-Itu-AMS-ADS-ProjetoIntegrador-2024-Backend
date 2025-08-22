package br.fatec.easycoast.mappers;

import java.util.List;

import br.fatec.easycoast.dtos.orderItem.OrderItemRequest;
import br.fatec.easycoast.dtos.orderItem.OrderItemResponse;
import br.fatec.easycoast.entities.OrderItem;

public class OrderItemMapper {
    public static OrderItem toEntity(OrderItemRequest request) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(request.quantity());
        orderItem.setObservations(request.observations());
        orderItem.setTotal(request.total());
        orderItem.setProduct(request.product());
        orderItem.setAddons(request.addons());
        orderItem.setOrder(request.order());
        return orderItem;

    }

    public static OrderItemResponse toDTO(OrderItem orderItem, Boolean isPost) {

        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getQuantity(),
                orderItem.getObservations(),
                orderItem.getTotal(),
                ProductMapper.toDTO(orderItem.getProduct()),
                AddonMapper.toListDTO(orderItem.getAddons(), isPost),
                orderItem.getOrder());
    }

    public static OrderItemResponse toDTO(OrderItem orderItem) {
        return toDTO(orderItem, null);
    }

    public static List<OrderItemResponse> toListDTO(List<OrderItem> orderItems) {
        List<OrderItemResponse> orderItemResponses = orderItems
                .stream()
                .map(orderItem -> toDTO(orderItem))
                .toList();
        return orderItemResponses;
    }

}
