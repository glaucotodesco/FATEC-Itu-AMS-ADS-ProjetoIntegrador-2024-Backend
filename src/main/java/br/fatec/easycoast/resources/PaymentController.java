package br.fatec.easycoast.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.fatec.easycoast.dtos.payment.PaymentRequest;
import br.fatec.easycoast.dtos.payment.PaymentResponse;
import br.fatec.easycoast.services.PaymentService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Integer id) {
        PaymentResponse payment = paymentService.getPayment(id);
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest request) {
        PaymentResponse createdPayment = paymentService.savePayment(request);
        URI location = URI.create("/payments/" + createdPayment.id());
        return ResponseEntity.created(location).body(createdPayment); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable Integer id, @RequestBody PaymentRequest request) {
        PaymentResponse updatedPayment = paymentService.updatePayment(id, request);
        return ResponseEntity.ok(updatedPayment); 
    }
}
