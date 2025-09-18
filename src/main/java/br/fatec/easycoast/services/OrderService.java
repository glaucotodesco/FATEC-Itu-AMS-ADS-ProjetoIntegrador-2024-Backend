package br.fatec.easycoast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.fatec.easycoast.dtos.order.OrderRequest;
import br.fatec.easycoast.dtos.order.OrderResponse;
import br.fatec.easycoast.entities.Card;
import br.fatec.easycoast.entities.Order;
import br.fatec.easycoast.mappers.OrderMapper;
import br.fatec.easycoast.repositories.CardRepository;
import br.fatec.easycoast.repositories.OrderRepository;
import br.fatec.easycoast.services.exceptions.DatabaseException;
import jakarta.persistence.EntityNotFoundException;

import java.time.Instant;
import java.util.List;

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
                .map(order -> OrderMapper.toDTO(order))
                .toList();
        return orderResponses;
    }

    public OrderResponse getOrder(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order doesn't exist!"));

        return OrderMapper.toDTO(order);
    }

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

    public OrderResponse updateOrder(Integer id, OrderRequest request) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Order not found by ID: " + id));

            // Lança uma exceção se o pedido já estiver fechado
            if (order.getClosingTime() != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot update a closed order.");
            }

            // Atualiza os campos do pedido
            order.setOpeningTime(request.openingTime());
            // O closingTime não é mais atualizado por aqui
            order.setCard(request.card());
            order.setSeat(request.seat());
            order.setEmployee(request.employee());
            order.setOrderItems(request.orderItems());

            orderRepository.save(order);
            return OrderMapper.toDTO(order);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Order update failed. Reason: " + e.getMessage());
        }
    }

    public void closeOrder(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found by ID: " + id));

        if (order.getClosingTime() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Order is already closed.");
        }

        order.setClosingTime(Instant.now());

        // Desassocia o pedido do card
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