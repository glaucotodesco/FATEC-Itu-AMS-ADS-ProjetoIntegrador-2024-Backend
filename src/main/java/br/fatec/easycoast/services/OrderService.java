package br.fatec.easycoast.services;

import br.fatec.easycoast.dtos.order.OrderRequest;
import br.fatec.easycoast.dtos.order.OrderResponse;
import br.fatec.easycoast.dtos.payment.PaymentStatus;
import br.fatec.easycoast.dtos.payment.ProcessPaymentRequest;
import br.fatec.easycoast.dtos.payment.ProcessPaymentRequest.PaymentPart;
import br.fatec.easycoast.entities.Card;
import br.fatec.easycoast.entities.Order;
import br.fatec.easycoast.entities.OrderItem;
import br.fatec.easycoast.entities.Payment;
import br.fatec.easycoast.entities.Product;
import br.fatec.easycoast.mappers.OrderMapper;
import br.fatec.easycoast.repositories.CardRepository;
import br.fatec.easycoast.repositories.OrderRepository;
import br.fatec.easycoast.repositories.PaymentRepository;
import br.fatec.easycoast.repositories.ProductRepository;
import br.fatec.easycoast.services.exceptions.DatabaseException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired private OrderRepository orderRepository;
    @Autowired private CardRepository cardRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private PaymentRepository paymentRepository;
    @Autowired private OrderItemService orderItemService;

    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream().map(OrderMapper::toDTO).toList();
    }

    public OrderResponse findActiveOrderByCardId(Integer cardId) {
        Order order = orderRepository.findByCardIdAndClosingTimeIsNull(cardId).orElse(null);

        if (order != null && (order.getCard() == null || !order.getCard().getActive())) {
            return null;
        }
        
        return OrderMapper.toDTO(order);
    }
    
    @Transactional
    public OrderResponse saveOrder(OrderRequest request) {
        Card card = cardRepository.findById(request.card().getId())
                .orElseThrow(() -> new EntityNotFoundException("Card not found!"));
        
        if (!card.getActive()) {
            throw new DatabaseException("Card is inactive and cannot be used for new orders.");
        }

        if (card.getOrder() != null) {
            throw new DatabaseException("Card already has an open order!");
        }

        Order order = OrderMapper.toEntity(request);
        order.setOpeningTime(Instant.now());
        order.setOrderItems(Collections.emptyList()); 
        
        Order savedOrder = orderRepository.save(order);

        if (request.orderItems() != null && !request.orderItems().isEmpty()) {
            List<OrderItem> items = request.orderItems().stream().map(item -> {
                item.setOrder(savedOrder);
                item.setTotal(orderItemService.calculateOrderItemTotal(item));
                return item;
            }).collect(Collectors.toList());
            savedOrder.setOrderItems(items);
        }
        
        updateTotal(savedOrder.getId());
        
        card.setOrder(savedOrder);
        cardRepository.save(card);
        
        return OrderMapper.toDTO(orderRepository.findById(savedOrder.getId()).get());
    }
    
    @Transactional
    public OrderResponse updateOrder(Integer id, OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found by ID: " + id));

        if (order.getCard() != null && !order.getCard().getActive()) {
            throw new IllegalStateException("Associated card is inactive/blocked. Order cannot be updated.");
        }

        order.getOrderItems().clear();

        if (request.orderItems() != null) {
            for (OrderItem requestedItem : request.orderItems()) {
                Product productEntity = productRepository.findById(requestedItem.getProduct().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Product not found"));

                OrderItem newItem = new OrderItem();
                newItem.setOrder(order);
                newItem.setProduct(productEntity);
                newItem.setQuantity(requestedItem.getQuantity());
                newItem.setObservations(requestedItem.getObservations());
                newItem.setAddons(requestedItem.getAddons());
                newItem.setReversed(requestedItem.getReversed());
                newItem.setTotal(orderItemService.calculateOrderItemTotal(newItem));
                
                order.getOrderItems().add(newItem);
            }
        }
        
        Order savedOrder = orderRepository.save(order);
        updateTotal(savedOrder.getId());
        
        return OrderMapper.toDTO(orderRepository.findById(savedOrder.getId()).get());
    }

    @Transactional
    public void updateTotal(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found to update total: " + id));
        
        double newTotal = order.getOrderItems().stream()
                .filter(item -> !Boolean.TRUE.equals(item.getReversed()))
                .mapToDouble(OrderItem::getTotal)
                .sum();
        
        order.setTotal(newTotal);
        orderRepository.save(order);
    }
    
    @Transactional
    public void processPayment(Integer orderId, ProcessPaymentRequest request) {
        Order updatedOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));

        long activeItemsCount = updatedOrder.getOrderItems().stream()
                .filter(item -> !Boolean.TRUE.equals(item.getReversed()))
                .count();

        if (activeItemsCount == 0) {
            throw new IllegalStateException("Cannot process payment for an order with no active items.");
        }

        if (updatedOrder.getCard() != null && !updatedOrder.getCard().getActive()) {
            throw new IllegalStateException("Associated card is inactive/blocked. Payment not allowed.");
        }

        updateTotal(orderId);
        
        Order orderWithTotal = orderRepository.findById(orderId).get();

        if (orderWithTotal.getClosingTime() != null) {
            throw new IllegalStateException("Este pedido já está fechado.");
        }
        
        double orderTotal = orderWithTotal.getTotal() != null ? orderWithTotal.getTotal() : 0.0;
        double orderTotalWithTip = orderTotal * 1.1; 
        double totalPaid = request.payments().stream().mapToDouble(PaymentPart::amount).sum();
        
        if (Math.abs(totalPaid - orderTotalWithTip) > 0.01) {
            throw new IllegalStateException(
                String.format("O valor pago (R$%.2f) não corresponde ao total do pedido com gorjeta (R$%.2f).", totalPaid, orderTotalWithTip)
            );
        }

        for (PaymentPart part : request.payments()) {
            Payment payment = new Payment();
            payment.setMethodPayment(part.method()); 
            payment.setPaymentValue(part.amount());   
            payment.setDate(Instant.now());         
            payment.setStatus(PaymentStatus.COMPLETED); 
            payment.setOrder(orderWithTotal);
            paymentRepository.save(payment);
        }

        this.closeOrder(orderId);
    }

    @Transactional
    public void closeOrder(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found by ID: " + id));
        if (order.getClosingTime() != null) {
            throw new IllegalStateException("Order is already closed.");
        }
        order.setClosingTime(Instant.now());
        
        Card card = order.getCard();
        if (card != null) {
            card.setOrder(null);
            cardRepository.save(card);
        }
        
        orderRepository.save(order);
    }
    
    @Transactional
    public void closeOrderWithoutPayment(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found by ID: " + id));
        if (order.getClosingTime() != null) {
            throw new IllegalStateException("Order is already closed.");
        }
        order.setClosingTime(Instant.now());

        Card card = order.getCard();
        if (card != null) {
            card.setOrder(null);
            cardRepository.save(card);
        }

        orderRepository.save(order);
    }


    @Transactional
    public void deactivateCard(Integer cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found by ID: " + cardId));
        
        card.setActive(false);
        cardRepository.save(card);
        logger.info("Card ID: {} has been deactivated.", cardId);
    }

    public OrderResponse getOrder(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order doesn't exist!"));
        return OrderMapper.toDTO(order);
    }
}