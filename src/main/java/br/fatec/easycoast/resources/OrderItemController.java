package br.fatec.easycoast.resources;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.orderItem.OrderItemRequest;
import br.fatec.easycoast.dtos.orderItem.OrderItemResponse;
import br.fatec.easycoast.dtos.orderItem.OrderItemOrderResponse;
import br.fatec.easycoast.dtos.orderItem.OrderItemResponseWithOrder;
import br.fatec.easycoast.mappers.OrderItemMapper;
import br.fatec.easycoast.services.OrderItemService;
import br.fatec.easycoast.services.OrderService;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public ResponseEntity<List<OrderItemResponseWithOrder>> getOrderItems() {
        return ResponseEntity.ok(OrderItemMapper.toListDTOWithOrder(orderItemService.getOrderItems()));
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderItemOrderResponse> getOrderItem(@PathVariable Integer id) {
        return ResponseEntity.ok(orderItemService.getOrderItemForOrder(id));
    }

    @PostMapping
    public ResponseEntity<OrderItemResponseWithOrder> saveOrderItem(@Valid @RequestBody OrderItemRequest request) {
        OrderItemResponseWithOrder orderItemResponse = orderItemService.saveOrderItem(request);
        if (request.order() != null) orderService.updateTotal(request.order().getId());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(orderItemResponse.id())
                .toUri();

        sendWebSocketMessages(orderItemResponse);

        return ResponseEntity.created(location).body(orderItemResponse);
    }

    private void sendWebSocketMessages(OrderItemResponseWithOrder orderItem) {
        try {
            Set<Integer> squareIds = orderItem.product().items().stream()
                .filter(item -> item.getItem().getSquare() != null)
                .map(item -> item.getItem().getSquare().getId())
                .collect(Collectors.toSet());
            
            for (Integer squareId : squareIds) {
                messagingTemplate.convertAndSend("/square/" + squareId, orderItem);
            }
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem WebSocket: " + e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderItemResponse> updateOrderItem(@Valid @PathVariable Integer id,
            @RequestBody OrderItemRequest request) {
        orderItemService.updateOrderItem(id, request);
        if (request.order() != null) orderService.updateTotal(request.order().getId());
        return ResponseEntity.ok().build();
    }
}