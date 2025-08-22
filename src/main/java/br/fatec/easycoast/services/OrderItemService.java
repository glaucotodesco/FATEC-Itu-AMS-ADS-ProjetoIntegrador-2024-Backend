package br.fatec.easycoast.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.orderItem.OrderItemRequest;
import br.fatec.easycoast.dtos.orderItem.OrderItemResponse;
import br.fatec.easycoast.entities.Addon;
import br.fatec.easycoast.entities.OrderItem;
import br.fatec.easycoast.mappers.OrderItemMapper;
import br.fatec.easycoast.repositories.AddonRepository;
import br.fatec.easycoast.repositories.OrderItemRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddonRepository addonRepository;

    public List<OrderItemResponse> getOrderItems() {
        List<OrderItemResponse> orderItemsResponse = orderItemRepository
                .findAll()
                .stream()
                .map(orderItem -> OrderItemMapper.toDTO(orderItem))
                .toList();
        return orderItemsResponse;

    }

    public OrderItemResponse getOrderItem(Integer id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Order Item not found"));
        return OrderItemMapper.toDTO(orderItem);
    }

    public OrderItemResponse saveOrderItem(OrderItemRequest request) {

        List<Integer> addonIds = request.addons().stream().map(Addon::getId).toList();
        int addonNumber = addonRepository.findAddonIfexists(addonIds, request.product().getId());
        if (addonNumber > 0) {
            throw new EntityNotFoundException("Addon incorrect");
        } else {
            OrderItem orderItem = orderItemRepository.save(OrderItemMapper.toEntity(request));

            return OrderItemMapper.toDTO(orderItem, true);

        }

    }

    public void updateOrderItem(Integer id, OrderItemRequest request) {

        List<Integer> addonIds = request.addons().stream().map(Addon::getId).toList();
        int addonNumber = addonRepository.findAddonIfexists(addonIds, request.product().getId());
        if (addonNumber > 0) {
            throw new EntityNotFoundException("Addon incorrect");
        } else {

            try {
                OrderItem orderItem = orderItemRepository.getReferenceById(id);
                orderItem.setQuantity(request.quantity());
                orderItem.setObservations(request.observations());
                orderItem.setTotal(request.total());
                orderItem.setProduct(request.product());
                orderItem.setAddons(request.addons());
                orderItem.setOrder(request.order());
                orderItemRepository.save(orderItem);

            } catch (EntityNotFoundException e) {
                throw new EntityNotFoundException("Not found Order Item.");
            }

        }

    }

}