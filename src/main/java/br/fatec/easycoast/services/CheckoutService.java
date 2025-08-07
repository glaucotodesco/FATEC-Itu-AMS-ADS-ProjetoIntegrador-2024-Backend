package br.fatec.easycoast.services;

import br.fatec.easycoast.dtos.checkout.CheckoutRequest;
import br.fatec.easycoast.dtos.checkout.CheckoutResponse;
import br.fatec.easycoast.entities.Checkout;
import br.fatec.easycoast.entities.Employee;
import br.fatec.easycoast.mappers.CheckoutMapper;
import br.fatec.easycoast.repositories.CheckoutRepository;
import br.fatec.easycoast.repositories.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final EmployeeRepository employeeRepository;

    public CheckoutService(CheckoutRepository checkoutRepository, EmployeeRepository employeeRepository) {
        this.checkoutRepository = checkoutRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public CheckoutResponse create(CheckoutRequest request) {
        Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Checkout checkout = CheckoutMapper.toEntity(request, employee);
        Checkout saved = checkoutRepository.save(checkout);
        return CheckoutMapper.toResponse(saved);
    }

    public List<CheckoutResponse> findAll() {
        return checkoutRepository.findAll()
                .stream()
                .map(CheckoutMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CheckoutResponse findById(Integer id) {
        Checkout checkout = checkoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Checkout not found"));
        return CheckoutMapper.toResponse(checkout);
    }

    @Transactional
    public CheckoutResponse update(Integer id, CheckoutRequest request) {
        Checkout existingCheckout = checkoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Checkout not found"));

        Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existingCheckout.setOpeningDate(request.openingDate());
        existingCheckout.setClosingDate(request.closingDate());
        existingCheckout.setEntryAmount(request.entryAmount());
        existingCheckout.setExitAmount(request.exitAmount());
        existingCheckout.setEmployee(employee);

        Checkout updatedCheckout = checkoutRepository.save(existingCheckout);
        return CheckoutMapper.toResponse(updatedCheckout);
    }
}
