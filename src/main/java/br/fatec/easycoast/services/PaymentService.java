package br.fatec.easycoast.services;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.payment.PaymentRequest;
import br.fatec.easycoast.dtos.payment.PaymentResponse;
import br.fatec.easycoast.entities.Payment;
import br.fatec.easycoast.mappers.PaymentMapper;
import br.fatec.easycoast.repositories.PaymentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public List<PaymentResponse> getPayments() {
        return paymentRepository.findAll().stream()
            .map(PaymentMapper::toResponse)
            .collect(Collectors.toList());
    }

    public PaymentResponse getPayment(Integer id) {
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
        return PaymentMapper.toResponse(payment);
    }

    public PaymentResponse savePayment(PaymentRequest request) {
        Payment payment = PaymentMapper.toEntity(request);
        payment = paymentRepository.save(payment);

        return PaymentMapper.toResponse(payment);
    }

    public PaymentResponse updatePayment(Integer id, PaymentRequest request) {
        Payment existingPayment = paymentRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        existingPayment.setPaymentValue(request.paymentValue());
        existingPayment.setMethodPayment(request.methodPayment());
        existingPayment.setDate(request.date());
        existingPayment.setStatus(request.status());
        existingPayment.setOrder(request.order());

        existingPayment = paymentRepository.save(existingPayment);
        return PaymentMapper.toResponse(existingPayment);
    }
}