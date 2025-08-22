package br.fatec.easycoast.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.customer.CustomerRequest;
import br.fatec.easycoast.dtos.customer.CustomerResponse;
import br.fatec.easycoast.entities.Customer;
import br.fatec.easycoast.mappers.CustomerMapper;
import br.fatec.easycoast.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerResponse> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> CustomerMapper.toDTO(customer))
                .toList();

    }

    public CustomerResponse getCustomer(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Customer not found."));
        return CustomerMapper.toDTO(customer);
    }

    public CustomerResponse saveCustomer(CustomerRequest request) {
        Customer customer = customerRepository.save(CustomerMapper.toEntity(request));
        return CustomerMapper.toDTO(customer);

    }

    public void updateCustomer(Integer id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Customer doesn't exists."));
        customer.setName(request.name());
        customer.setPhone(request.phone());
        customer.setBirthDate(request.birthDate());
        customer.setEmail(request.email());
        customerRepository.save(customer);

    }

}
