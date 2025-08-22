package br.fatec.easycoast.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.order.OrderRequest;
import br.fatec.easycoast.dtos.order.OrderResponse;
import br.fatec.easycoast.services.OrderService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RequestMapping("orders")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

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
        return ResponseEntity.created(location).body(orderResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderResponse> updateOrder(@Valid @PathVariable int id, @RequestBody OrderRequest request) {
        OrderResponse orderResponse = orderService.updateOrder(id, request);
        return ResponseEntity.ok(orderResponse);
    }

}
