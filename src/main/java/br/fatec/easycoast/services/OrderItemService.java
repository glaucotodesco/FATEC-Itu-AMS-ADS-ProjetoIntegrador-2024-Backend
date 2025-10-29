package br.fatec.easycoast.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.addonCategory.AddonType;
import br.fatec.easycoast.dtos.orderItem.OrderItemAddon;
import br.fatec.easycoast.dtos.orderItem.OrderItemRequest;
import br.fatec.easycoast.dtos.orderItem.OrderItemResponse;
import br.fatec.easycoast.dtos.product.ProductResponse;
import br.fatec.easycoast.entities.Addon;
import br.fatec.easycoast.entities.OrderItem;
import br.fatec.easycoast.mappers.OrderItemMapper;
import br.fatec.easycoast.repositories.AddonRepository;
import br.fatec.easycoast.repositories.OrderItemRepository;
import br.fatec.easycoast.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddonRepository addonRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddonService addonService;

    @Autowired
    private ProductService productService;

    public List<OrderItem> getOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItemResponse getOrderItem(Integer id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order Item not found!"));
        return OrderItemMapper.toDTO(orderItem);
    }

    private void checkAddons(OrderItemAddon addon){
        Addon aux = addonRepository.getReferenceById(addon.getAddon().getId());
        //Check quantity value
        if (addon.getQuantity() != null && addon.getQuantity() < 1) throw new IllegalArgumentException("Quantity must be at least 1");
        //Check if it is quantitative
        if (aux.getAddonCategory().getType() == AddonType.GENERAL && addon.getQuantity() == null) throw new IllegalArgumentException("Max quantity is required for this addon!");
        //Check the max quantity
        if (aux.getMaxQuantity() != null && addon.getQuantity() != null && aux.getMaxQuantity() < addon.getQuantity()) throw new IllegalArgumentException("Max quantity exceeded!");
    }

    public OrderItemResponse saveOrderItem(OrderItemRequest request) {
        orderRepository.findById(request.order().getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + request.order().getId()));
        //Check addons quantity
        request.addons().forEach(a -> checkAddons(a));
        //Check if addons are from the same product
        List<Integer> addonIds = request.addons().stream().map(a -> a.getAddon().getId()).collect(Collectors.toList());
        if (!addonIds.isEmpty()) {
            int addonNumber = addonRepository.findAddonIfexists(addonIds, request.product().getId());
            if (addonNumber > 0) {
                throw new EntityNotFoundException("Addon incorrect!");
            }
        }

        OrderItem orderItem = OrderItemMapper.toEntity(request);
        orderItem.setTotal(calculateOrderItemTotal(orderItem));
        return OrderItemMapper.toDTO(orderItemRepository.save(orderItem), true);
    }

    public void updateOrderItem(Integer id, OrderItemRequest request) {
        orderRepository.findById(request.order().getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + request.order().getId()));
        
        request.addons().forEach(a -> checkAddons(a));
        List<Integer> addonIds = request.addons().stream().map(a -> a.getAddon().getId()).collect(Collectors.toList());
        if (!addonIds.isEmpty()) {
            int addonNumber = addonRepository.findAddonIfexists(addonIds, request.product().getId());
            if (addonNumber > 0) {
                throw new EntityNotFoundException("Addon incorrect!");
            }
        }

        try {
            OrderItem orderItem = orderItemRepository.getReferenceById(id);
            orderItem.setQuantity(request.quantity());
            orderItem.setObservations(request.observations());
            orderItem.setProduct(request.product());
            orderItem.setAddons(request.addons());
            orderItem.setOrder(request.order());
            orderItem.setReversed(request.reversed());
            orderItem.setTotal(calculateOrderItemTotal(orderItem));
            orderItemRepository.save(orderItem);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Not found Order Item!");
        }
    }

    public double calculateOrderItemTotal(OrderItem orderItem) {
        double total = 0.0;
        if (orderItem.getProduct() != null && orderItem.getQuantity() != null) {
            ProductResponse product = productService.getProductById(orderItem.getProduct().getId());
            double productPrice = product.price() - (product.price() * product.discount() / 100);

            double addonsPrice = 0.0;
            if (orderItem.getAddons() != null) {
                addonsPrice = orderItem.getAddons().stream()
                        .mapToDouble(addon -> addonService.getAddonById(addon.getAddon().getId()).price())
                        .sum();
            }

            total = (productPrice + addonsPrice) * orderItem.getQuantity();
        }
        return new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}