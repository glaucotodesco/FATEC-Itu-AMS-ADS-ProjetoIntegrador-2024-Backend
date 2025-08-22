package br.fatec.easycoast.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import br.fatec.easycoast.services.OrderItemService;

@CrossOrigin
@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<List<OrderItemResponse>> getOrderItems() {
        return ResponseEntity.ok(orderItemService.getOrderItems());
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderItemResponse> getOrderItem(@PathVariable Integer id) {
        return ResponseEntity.ok(orderItemService.getOrderItem(id));
    }

    @PostMapping
    public ResponseEntity<OrderItemResponse> saveOrderItem(@RequestBody OrderItemRequest request) {
        OrderItemResponse orderItemResponse = orderItemService.saveOrderItem(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(orderItemResponse.id())
                .toUri();
        return ResponseEntity.created(location).body(orderItemResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderItemResponse> updateOrderItem(@PathVariable Integer id,
            @RequestBody OrderItemRequest request) {
        orderItemService.updateOrderItem(id, request);
        return ResponseEntity.ok().build();
    }
}
