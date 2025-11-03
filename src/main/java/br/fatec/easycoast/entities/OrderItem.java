package br.fatec.easycoast.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.fatec.easycoast.dtos.orderItem.OrderItemAddon;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_ORDERITEM")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantity;

    private String observations;

    private Double total;

    private Boolean reversed = false; 
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ElementCollection
    @CollectionTable(name = "TBL_ORDERITEM_ADDONS", joinColumns = @JoinColumn(name = "ORDER_ITEM_ID"))
    private List<OrderItemAddon> addons;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    @JsonBackReference
    private Order order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<OrderItemAddon> getAddons() {
        return addons;
    }

    public void setAddons(List<OrderItemAddon> addons) {
        this.addons = addons;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Boolean getReversed() {
        return reversed;
    }

    public void setReversed(Boolean reversed) {
        this.reversed = reversed;
    }
}