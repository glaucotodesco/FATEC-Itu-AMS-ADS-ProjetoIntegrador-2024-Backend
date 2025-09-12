package br.fatec.easycoast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.order.OrderRequest;
import br.fatec.easycoast.dtos.order.OrderResponse;
import br.fatec.easycoast.entities.Card;
import br.fatec.easycoast.entities.Order;
import br.fatec.easycoast.mappers.OrderMapper;
import br.fatec.easycoast.repositories.CardRepository;
import br.fatec.easycoast.repositories.OrderRepository;
import br.fatec.easycoast.services.exceptions.DatabaseException;
import jakarta.persistence.EntityNotFoundException;

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

            // Atualiza os campos do pedido
            order.setOpeningTime(request.openingTime());
            order.setClosingTime(request.closingTime());
            order.setCard(request.card());
            order.setSeat(request.seat());
            order.setEmployee(request.employee());
            order.setOrderItems(request.orderItems());

            // Se o pedido está sendo fechado (closingTime não é nulo)
            if (request.closingTime() != null) {
                // Busca o card completo do banco de dados para evitar o erro de campos nulos
                Card card = cardRepository.findById(order.getCard().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Card not found!"));
                
                // Desassocia o pedido do card
                card.setOrder(null);
                cardRepository.save(card);
            }

            orderRepository.save(order);
            return OrderMapper.toDTO(order);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Order update failed. Reason: " + e.getMessage());
        }
    }

    public void updateTotal(Integer id) {
        Order order = orderRepository.getReferenceById(id);
        order.setTotal(order.getOrderItems().stream()
                            .mapToDouble(item -> orderItemService.getOrderItem(item.getId()).total())
                            .sum());
        orderRepository.save(order);
    }
}