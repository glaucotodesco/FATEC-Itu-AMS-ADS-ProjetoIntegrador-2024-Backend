package br.fatec.easycoast.mappers;

import java.util.Collections;
import java.util.List;

import br.fatec.easycoast.dtos.orderItem.OrderItemAddon;
import br.fatec.easycoast.dtos.orderItem.OrderItemAddonResponse;
import br.fatec.easycoast.dtos.orderItem.OrderItemRequest;
import br.fatec.easycoast.dtos.orderItem.OrderItemResponse;
import br.fatec.easycoast.dtos.orderItem.OrderItemResponseWithOrder;
import br.fatec.easycoast.entities.OrderItem;

public class OrderItemMapper {
    public static OrderItem toEntity(OrderItemRequest request) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(request.quantity());
        orderItem.setObservations(request.observations());
        orderItem.setProduct(request.product());
        orderItem.setAddons(request.addons());
        orderItem.setOrder(request.order());
        if (request.reversed() != null) {
            orderItem.setReversed(request.reversed());
        }
        return orderItem;
    }

    public static OrderItemResponse toDTO(OrderItem orderItem, Boolean isPost) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getQuantity(),
                orderItem.getObservations(),
                orderItem.getTotal(),
                orderItem.getReversed(),
                orderItem.getProduct() != null ? ProductMapper.toDTO(orderItem.getProduct()) : null,
                orderItem.getAddons() != null ? addonToResponse(orderItem.getAddons()) : null
        );
    }

    private static List<OrderItemAddonResponse> addonToResponse(List<OrderItemAddon> addons) {
        if (addons != null) {
            return addons
                    .stream()
                    .map(a -> {
                        return new OrderItemAddonResponse(AddonMapper.toDTO(a.getAddon()), a.getQuantity());
                    })
                    .toList();
        }
        return Collections.emptyList();
    }

    public static OrderItemResponse toDTO(OrderItem orderItem) {
        return toDTO(orderItem, null);
    }

    public static OrderItemResponseWithOrder toDTOWithOrder(OrderItem orderItem, Boolean isPost) {
        return new OrderItemResponseWithOrder(
                orderItem.getId(),
                orderItem.getQuantity(),
                orderItem.getObservations(),
                orderItem.getTotal(),
                orderItem.getReversed(),
                orderItem.getProduct() != null ? ProductMapper.toDTO(orderItem.getProduct()) : null,
                orderItem.getAddons() != null ? addonToResponse(orderItem.getAddons()) : null,
                orderItem.getOrder() != null ? OrderMapper.toDTO(orderItem.getOrder()) : null
        );
    }

    public static OrderItemResponseWithOrder toDTOWithOrder(OrderItem orderItem) {
        return toDTOWithOrder(orderItem, null);
    }

    public static List<OrderItemResponse> toListDTO(List<OrderItem> orderItems) {
        if (orderItems != null) {
            return orderItems
                    .stream()
                    .map(OrderItemMapper::toDTO)
                    .toList();
        }
        return Collections.emptyList();
    }

    public static List<OrderItemResponseWithOrder> toListDTOWithOrder(List<OrderItem> orderItems) {
        if (orderItems != null) {
            return orderItems
                    .stream()
                    .map(OrderItemMapper::toDTOWithOrder)
                    .toList();
        }
        return Collections.emptyList();
    }
}