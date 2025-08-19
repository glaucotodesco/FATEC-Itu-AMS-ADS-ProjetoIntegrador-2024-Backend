package br.fatec.easycoast.resources;

import br.fatec.easycoast.dtos.checkout.CheckoutRequest;
import br.fatec.easycoast.dtos.checkout.CheckoutResponse;
import br.fatec.easycoast.services.CheckoutService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/checkouts")
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<CheckoutResponse> create(@RequestBody CheckoutRequest request) {
        CheckoutResponse response = checkoutService.create(request);

        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(response.id())
                        .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CheckoutResponse>> findAll() {
        return ResponseEntity.ok(checkoutService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckoutResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(checkoutService.findById(id));
    }

  @PutMapping("/{id}")
    public ResponseEntity<CheckoutResponse> update(@PathVariable Integer id, @RequestBody CheckoutRequest request) {
        return ResponseEntity.ok(checkoutService.update(id, request));
    }
}

