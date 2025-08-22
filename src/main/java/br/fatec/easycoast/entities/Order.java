package br.fatec.easycoast.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "TBL_ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Instant openingTime;
    private Instant closingTime;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "CARD_ID")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "SEAT_ID")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Instant openingTime) {
        this.openingTime = openingTime;
    }

    public Instant getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Instant closingTime) {
        this.closingTime = closingTime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

}