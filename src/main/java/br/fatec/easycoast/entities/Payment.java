package br.fatec.easycoast.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import java.time.Instant;

import br.fatec.easycoast.dtos.payment.PaymentMethod;
import br.fatec.easycoast.dtos.payment.PaymentStatus;

@Entity
@Table(name = "TBL_PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double PaymentValue;

    @Enumerated(EnumType.STRING)
    private PaymentMethod methodPayment;

    private Instant date;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPaymentValue() {
        return PaymentValue;
    }

    public void setPaymentValue(Double PaymentValue) {
        this.PaymentValue = PaymentValue;
    }

    public PaymentMethod getMethodPayment() {
        return methodPayment;
    }

    public void setMethodPayment(PaymentMethod methodPayment) {
        this.methodPayment = methodPayment;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
