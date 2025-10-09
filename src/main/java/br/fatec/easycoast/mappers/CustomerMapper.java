package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.customer.CustomerRequest;
import br.fatec.easycoast.dtos.customer.CustomerResponse;
import br.fatec.easycoast.entities.Customer;

public class CustomerMapper {
    public static Customer toEntity(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setPhone(request.phone());
        customer.setBirthDate(request.birthDate());
        customer.setEmail(request.email());
        return customer;

    }

    public static CustomerResponse toDTO(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getBirthDate(),
                customer.getEmail());

    }
}
