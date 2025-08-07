package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.payment.PaymentRequest;
import br.fatec.easycoast.dtos.payment.PaymentResponse;
import br.fatec.easycoast.entities.Order;
import br.fatec.easycoast.entities.Payment;

public class PaymentMapper {

    public static Payment toEntity(PaymentRequest request, Order order) {
        Payment payment = new Payment();
        payment.setValue(request.value());
        payment.setMethodPayment(request.methodPayment());
        payment.setDate(request.date());
        payment.setStatus(request.status());
        payment.setOrder(order);
        return payment;
    }

    public static PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
            payment.getId(),
            payment.getValue(),
            payment.getMethodPayment(),
            payment.getDate(),
            payment.getStatus(),
            payment.getOrder()
        );
    }
}