package br.fatec.easycoast.services;

import br.fatec.easycoast.dtos.checkout.CheckoutRequest;
import br.fatec.easycoast.dtos.checkout.CheckoutResponse;
import br.fatec.easycoast.entities.Checkout;
import br.fatec.easycoast.mappers.CheckoutMapper;
import br.fatec.easycoast.repositories.CheckoutRepository;
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

    public CheckoutResponse create(CheckoutRequest request) {
        Checkout saved = checkoutRepository.save(CheckoutMapper.toEntity(request));
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
                .orElseThrow(() -> new EntityNotFoundException("Checkout not found"));
        return CheckoutMapper.toResponse(checkout);
    }

    @Transactional
    public CheckoutResponse update(Integer id, CheckoutRequest request) {
        Checkout existingCheckout = checkoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Checkout not found"));

        existingCheckout.setOpeningDate(request.openingDate());
        existingCheckout.setClosingDate(request.closingDate());
        existingCheckout.setEntryAmount(request.entryAmount());
        existingCheckout.setExitAmount(request.exitAmount());
        existingCheckout.setEmployee(request.employee());

        Checkout updatedCheckout = checkoutRepository.save(existingCheckout);
        return CheckoutMapper.toResponse(updatedCheckout);
    }
}
