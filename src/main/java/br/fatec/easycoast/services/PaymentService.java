package br.fatec.easycoast.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.payment.PaymentRequest;
import br.fatec.easycoast.dtos.payment.PaymentResponse;
import br.fatec.easycoast.entities.Order;
import br.fatec.easycoast.entities.Payment;
import br.fatec.easycoast.mappers.PaymentMapper;
import br.fatec.easycoast.repositories.OrderRepository;
import br.fatec.easycoast.repositories.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public List<PaymentResponse> getPayments() {
        return paymentRepository.findAll().stream()
            .map(PaymentMapper::toResponse)
            .collect(Collectors.toList());
    }

    public PaymentResponse getPayment(Integer id) {
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        Order order = payment.getOrder(); 
        PaymentResponse response = PaymentMapper.toResponse(payment);

        response = new PaymentResponse(response.id(), response.PaymentValue(), response.methodPayment(),
            response.date(), response.status(), order);

        return response;
    }

    public PaymentResponse savePayment(PaymentRequest request) {
        Order order = orderRepository.findById(request.orderId())
            .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        Payment payment = PaymentMapper.toEntity(request, order);
        payment = paymentRepository.save(payment);

        return PaymentMapper.toResponse(payment);
    }

    public PaymentResponse updatePayment(Integer id, PaymentRequest request) {
        Payment existingPayment = paymentRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        Order order = orderRepository.findById(request.orderId())
            .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        existingPayment.setPaymentValue(request.PaymentValue());
        existingPayment.setMethodPayment(request.methodPayment());
        existingPayment.setDate(request.date());
        existingPayment.setStatus(request.status());
        existingPayment.setOrder(order);

        existingPayment = paymentRepository.save(existingPayment);
        return PaymentMapper.toResponse(existingPayment);
    }
}
