package br.fatec.easycoast.resources;

import br.fatec.easycoast.dtos.checkout.CheckoutRequest;
import br.fatec.easycoast.dtos.checkout.CheckoutResponse;
import br.fatec.easycoast.services.CheckoutService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkouts")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public ResponseEntity<CheckoutResponse> create(@RequestBody CheckoutRequest request) {
        return ResponseEntity.ok(checkoutService.create(request));
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

