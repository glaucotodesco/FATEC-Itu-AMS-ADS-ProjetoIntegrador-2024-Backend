package br.fatec.easycoast.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.orderItem.OrderItemRequest;
import br.fatec.easycoast.dtos.orderItem.OrderItemResponse;
import br.fatec.easycoast.dtos.product.ProductResponse;
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

    @Autowired
    private AddonService addonService;

    @Autowired
    private ProductService productService;

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
                        () -> new EntityNotFoundException("Order Item not found!"));
        return OrderItemMapper.toDTO(orderItem);
    }

    public OrderItemResponse saveOrderItem(OrderItemRequest request) {

        List<Integer> addonIds = request.addons().stream().map(Addon::getId).toList();
        int addonNumber = addonRepository.findAddonIfexists(addonIds, request.product().getId());
        if (addonNumber > 0) {
            throw new EntityNotFoundException("Addon incorrect!");
        } else {
            OrderItem orderItem = OrderItemMapper.toEntity(request);

            // Calculating the total, by the sum of the price of the addons
            double total = orderItem.getAddons()
                                    .stream()
                                    .mapToDouble(addon -> addonService.getAddonById(addon.getId()).price())
                                    .sum();
            // Then, get the info of the product
            ProductResponse product = productService.getProductById(orderItem.getProduct().getId());
            // And calculate the price, by subtracting the price by the discont, and sum with the addons and multiply by the quantity
            total = ((product.price() - (product.price() / 100 * product.discount())) + total) * orderItem.getQuantity();
            // Use BigDecimal to preserve the two decimal places, and set the total
            orderItem.setTotal(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue());

            return OrderItemMapper.toDTO(orderItemRepository.save(orderItem), true);

        }

    }

    public void updateOrderItem(Integer id, OrderItemRequest request) {

        List<Integer> addonIds = request.addons().stream().map(Addon::getId).toList();
        int addonNumber = addonRepository.findAddonIfexists(addonIds, request.product().getId());
        if (addonNumber > 0) {
            throw new EntityNotFoundException("Addon incorrect!");
        } else {

            try {
                OrderItem orderItem = orderItemRepository.getReferenceById(id);
                orderItem.setQuantity(request.quantity());
                orderItem.setObservations(request.observations());
                orderItem.setProduct(request.product());
                orderItem.setAddons(request.addons());
                orderItem.setOrder(request.order());

                // Calculating the total, by the sum of the price of the addons
                double total = orderItem.getAddons()
                                        .stream()
                                        .mapToDouble(addon -> addonService.getAddonById(addon.getId()).price())
                                        .sum();
                // Then, get the info of the product
                ProductResponse product = productService.getProductById(orderItem.getProduct().getId());
                // And calculate the price, by subtracting the price by the discont, and sum with the addons and multiply by the quantity
                total = ((product.price() - (product.price() / 100 * product.discount())) + total) * orderItem.getQuantity();
                // Use BigDecimal to preserve the two decimal places, and set the total
                orderItem.setTotal(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue());

                orderItemRepository.save(orderItem);

            } catch (EntityNotFoundException e) {
                throw new EntityNotFoundException("Not found Order Item!");
            }

        }

    }

}