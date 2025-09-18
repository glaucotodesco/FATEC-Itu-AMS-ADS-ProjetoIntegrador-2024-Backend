// renatoluizcardoso/fatec-itu-ams-ads-projetointegrador-2025-backend/FATEC-Itu-AMS-ADS-ProjetoIntegrador-2025-Backend-feature-issue122/src/main/java/br/fatec/easycoast/services/OrderService.java
package br.fatec.easycoast.services;

import br.fatec.easycoast.dtos.order.OrderRequest;
import br.fatec.easycoast.dtos.order.OrderResponse;
import br.fatec.easycoast.entities.Card;
import br.fatec.easycoast.entities.Order;
import br.fatec.easycoast.mappers.OrderMapper;
import br.fatec.easycoast.repositories.CardRepository;
import br.fatec.easycoast.repositories.OrderRepository;
import br.fatec.easycoast.services.exceptions.DatabaseException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private OrderItemService orderItemService;

    public List<OrderResponse> getOrders() {
        List<OrderResponse> orderResponses = orderRepository.findAll()
                .stream()
                .map(OrderMapper::toDTO)
                .toList();
        return orderResponses;
    }

    public OrderResponse getOrder(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order doesn't exist!"));

        return OrderMapper.toDTO(order);
    }

    @Transactional
    public OrderResponse saveOrder(OrderRequest request) {
        Card card = cardRepository.findById(request.card().getId())
                .orElseThrow(() -> new EntityNotFoundException("Card not found!"));

        if (card.getOrder() != null) {
            throw new DatabaseException("Card already has an open order!");
        }

        Order order = OrderMapper.toEntity(request);
        order = orderRepository.save(order);

        card.setOrder(order);
        cardRepository.save(card);

        return OrderMapper.toDTO(order);
    }

    @Transactional
    public OrderResponse updateOrder(Integer id, OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found by ID: " + id));

        if (order.getClosingTime() != null) {
            // ✅ MUDANÇA AQUI
            throw new IllegalStateException("Cannot update a closed order.");
        }

        Card originalCard = order.getCard();
        Card newCardFromRequest = request.card();

        if (newCardFromRequest != null && !Objects.equals(newCardFromRequest.getId(), originalCard.getId())) {
            
            Card newCard = cardRepository.findById(newCardFromRequest.getId())
                    .orElseThrow(() -> new EntityNotFoundException("New card not found with ID: " + newCardFromRequest.getId()));

            if (newCard.getOrder() != null) {
                // ✅ MUDANÇA AQUI
                throw new IllegalStateException("Card " + newCard.getId() + " is already in use.");
            }
            
            if (originalCard != null) {
                originalCard.setOrder(null);
                cardRepository.save(originalCard);
            }
            
            order.setCard(newCard);
            newCard.setOrder(order);
            cardRepository.save(newCard);
        }

        order.setOpeningTime(request.openingTime());
        order.setSeat(request.seat());
        order.setEmployee(request.employee());
        order.setOrderItems(request.orderItems());

        Order updatedOrder = orderRepository.save(order);
        return OrderMapper.toDTO(updatedOrder);
    }

    @Transactional
    public void closeOrder(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found by ID: " + id));

        if (order.getClosingTime() != null) {
            // ✅ MUDANÇA AQUI
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

    public void updateTotal(Integer id) {
        Order order = orderRepository.getReferenceById(id);
        order.setTotal(order.getOrderItems().stream()
                            .mapToDouble(item -> orderItemService.getOrderItem(item.getId()).total())
                            .sum());
        orderRepository.save(order);
    }
}