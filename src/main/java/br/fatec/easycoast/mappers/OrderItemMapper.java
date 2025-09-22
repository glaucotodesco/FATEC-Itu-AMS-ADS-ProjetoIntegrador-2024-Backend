package br.fatec.easycoast.mappers;

import java.util.Collections;
import java.util.List;

import br.fatec.easycoast.dtos.orderItem.OrderItemRequest;
import br.fatec.easycoast.dtos.orderItem.OrderItemResponse;
import br.fatec.easycoast.entities.OrderItem;

public class OrderItemMapper {
    public static OrderItem toEntity(OrderItemRequest request) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(request.quantity());
        orderItem.setObservations(request.observations());
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
                orderItem.getProduct() != null ? ProductMapper.toDTO(orderItem.getProduct()) : null,
                orderItem.getAddons() != null ? AddonMapper.toListDTO(orderItem.getAddons(), isPost) : null
        // O último parâmetro "orderItem.getOrder()" foi REMOVIDO
        );
    }

    public static OrderItemResponse toDTO(OrderItem orderItem) {
        return toDTO(orderItem, null);
    }

    public static List<OrderItemResponse> toListDTO(List<OrderItem> orderItems) {
        if (orderItems != null) {
            List<OrderItemResponse> orderItemResponses = orderItems
                    .stream()
                    .map(orderItem -> toDTO(orderItem))
                    .toList();
            return orderItemResponses;
        }
        return Collections.emptyList();
    }
}