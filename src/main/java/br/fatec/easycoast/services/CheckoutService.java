package br.fatec.easycoast.services;

import br.fatec.easycoast.dtos.checkout.CheckoutRequest;
import br.fatec.easycoast.dtos.checkout.CheckoutResponse;
import br.fatec.easycoast.entities.Checkout;
import br.fatec.easycoast.entities.Employee;
import br.fatec.easycoast.mappers.CheckoutMapper;
import br.fatec.easycoast.repositories.CheckoutRepository;
import br.fatec.easycoast.repositories.EmployeeRepository;
import br.fatec.easycoast.services.exceptions.DatabaseException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutService {
    @Autowired
    private CheckoutRepository checkoutRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public CheckoutResponse create(CheckoutRequest request) {
        try{
            Employee employee = employeeRepository.findById(request.employeeId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
            Checkout checkout = CheckoutMapper.toEntity(request, employee);
            Checkout saved = checkoutRepository.save(checkout);
            return CheckoutMapper.toResponse(saved);
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Employee ID not given");
        }
    }

    public List<CheckoutResponse> findAll() {
        return checkoutRepository.findAll()
                .stream()
                .map(CheckoutMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CheckoutResponse findById(Integer id) {
        Checkout checkout = checkoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Checkout not found"));
        return CheckoutMapper.toResponse(checkout);
    }

    @Transactional
    public CheckoutResponse update(Integer id, CheckoutRequest request) {
        Checkout existingCheckout = checkoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Checkout not found"));

        Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        existingCheckout.setOpeningDate(request.openingDate());
        existingCheckout.setClosingDate(request.closingDate());
        existingCheckout.setEntryAmount(request.entryAmount());
        existingCheckout.setExitAmount(request.exitAmount());
        existingCheckout.setEmployee(employee);

        Checkout updatedCheckout = checkoutRepository.save(existingCheckout);
        return CheckoutMapper.toResponse(updatedCheckout);
    }
}
