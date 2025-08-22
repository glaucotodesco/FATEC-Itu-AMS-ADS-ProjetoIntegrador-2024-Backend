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

import br.fatec.easycoast.dtos.customer.CustomerRequest;
import br.fatec.easycoast.dtos.customer.CustomerResponse;
import br.fatec.easycoast.services.CustomerService;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());

    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable int id) {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> saveCustomer(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse customerResponse = customerService.saveCustomer(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customerResponse.id())
                .toUri();
        return ResponseEntity.created(location).body(customerResponse);

    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateCustomer(@Valid @PathVariable int id, @RequestBody CustomerRequest request) {
        customerService.updateCustomer(id, request);
        return ResponseEntity.ok().build();
    }

}
