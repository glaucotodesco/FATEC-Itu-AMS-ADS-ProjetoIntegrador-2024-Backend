package br.fatec.easycoast.services;

import java.util.List;

import org.hibernate.annotations.SoftDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.customer.CustomerRequest;
import br.fatec.easycoast.dtos.customer.CustomerResponse;
import br.fatec.easycoast.entities.Customer;
import br.fatec.easycoast.mappers.CustomerMapper;
import br.fatec.easycoast.repositories.CustomerRepository;
import br.fatec.easycoast.services.auth.TokenProvider;
import jakarta.persistence.EntityNotFoundException;

@Service
@SoftDelete
public class CustomerService {
    @Value("${auth.disabled}")
    private boolean authDisabled;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TokenProvider tokenProvider;

    public List<CustomerResponse> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> CustomerMapper.toDTO(customer))
                .toList();

    }

    public CustomerResponse getCustomer(Integer id) {
        //Verify if the Customer is the one doing the request
        if(!authDisabled) if(!verifyTheUser(id)) throw new IllegalArgumentException("You can't GET this customer!");

        Customer customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Customer not found!"));
        return CustomerMapper.toDTO(customer);
    }

    //Get the own user
    public CustomerResponse getSelf(){
        if(authDisabled) throw new IllegalArgumentException("This endpoint is unavailable because authentication is deactivated");
        
        Customer customer = customerRepository.findById(getTheUser().getId())
                            .orElseThrow(() -> new EntityNotFoundException("Customer not found!"));
        return CustomerMapper.toDTO(customer);
    }

    public CustomerResponse saveCustomer(CustomerRequest request) {
        try {
            Customer customer = CustomerMapper.toEntity(request);
            customer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
            customer = customerRepository.save(customer);
            return CustomerMapper.toDTO(customer);
        } catch (DataIntegrityViolationException e){
            throw new IllegalArgumentException("A Customer with this email already exists!");
        }
    }

    public String updateCustomer(Integer id, CustomerRequest request) {
        //Verify if the Customer is the one doing the request
        if(!authDisabled) if(!verifyTheUser(id)) throw new IllegalArgumentException("You can't PUT this customer!");
        try {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(
                            () -> new EntityNotFoundException("Customer doesn't exists!"));
            customer.setName(request.name());
            customer.setPhone(request.phone());
            customer.setBirthDate(request.birthDate());
            customer.setEmail(request.email());
            customer.setPassword(new BCryptPasswordEncoder().encode(request.password()));
            return tokenProvider.generateAccessToken(customerRepository.save(customer));
        } catch (DataIntegrityViolationException e){
            throw new IllegalArgumentException("A Customer with this email already exists!");
        }
    }

    public void deleteCustomer(int id) {
        //Verify if the Customer is the one doing the request
        if(!authDisabled) if(!verifyTheUser(id)) throw new IllegalArgumentException("You can't DELETE this customer!");
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Customer not found!");
        }
    }

    //Get the user of the request
    private Customer getTheUser(){
        //Get the authentication of the request
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Verify if the Authentication is correct
        if(auth != null && auth.getPrincipal() instanceof UserDetails){
            //Get the User
            return (Customer) auth.getPrincipal();
        } else {
            return null;
        }
    }

    private boolean verifyTheUser(int id){
        //Get the authentication of the request
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Verify if the Authentication is correct
        if(auth != null && auth.getPrincipal() instanceof UserDetails){
            //Get the User
            Customer customer = (Customer) auth.getPrincipal();
            //Check the id
            return id == customer.getId();
        } else {
            return false;
        }
    }
}
