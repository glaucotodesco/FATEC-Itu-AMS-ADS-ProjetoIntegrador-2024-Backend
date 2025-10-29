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

import br.fatec.easycoast.dtos.order.OrderRequest;
import br.fatec.easycoast.dtos.order.OrderResponse;
import br.fatec.easycoast.dtos.payment.ProcessPaymentRequest; 
import br.fatec.easycoast.services.OrderService;
import jakarta.validation.Valid;

@CrossOrigin
@RequestMapping("orders")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping()
    public ResponseEntity<List<OrderResponse>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponse> getOrder(@Valid @PathVariable int id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> saveOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse orderResponse = orderService.saveOrder(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderResponse.id())
                .toUri();
                
        sendWebSocketMessages(orderResponse);
        
        return ResponseEntity.created(location).body(orderResponse);
    }
    
    private void sendWebSocketMessages(OrderResponse order) {
        try {
            Set<Integer> squareIds = order.orderItems().stream()
                .flatMap(orderItem -> orderItem.product().items().stream())
                .filter(item -> item.getItem().getSquare() != null)
                .map(item -> item.getItem().getSquare().getId())
                .collect(Collectors.toSet());

            for (Integer squareId : squareIds) {
                messagingTemplate.convertAndSend("/square/" + squareId, order);
            }
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem WebSocket: " + e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderResponse> updateOrder(@Valid @PathVariable int id, @RequestBody OrderRequest request) {
        OrderResponse orderResponse = orderService.updateOrder(id, request);
        return ResponseEntity.ok(orderResponse);
    }

    @PutMapping("{id}/close")
    public ResponseEntity<Void> closeOrder(@PathVariable int id) {
        orderService.closeOrder(id);
        return ResponseEntity.noContent().build();
    }

     
    
    @GetMapping("/by-card/{cardId}")
    public ResponseEntity<OrderResponse> getOrderByCardId(@PathVariable Integer cardId) {
        OrderResponse order = orderService.findActiveOrderByCardId(cardId);

        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<Void> processPayment(@PathVariable Integer id, @Valid @RequestBody ProcessPaymentRequest request) {
        orderService.processPayment(id, request);
        return ResponseEntity.ok().build();
    }
}