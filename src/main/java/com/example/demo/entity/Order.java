package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    private Double totalAmount;

    private String status;


    // ==========================================
    // USER
    // ==========================================

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


    // ==========================================
    // ORDER ITEMS
    // ==========================================

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL
    )
    private List<OrderItem> orderItems;


    // ==========================================
    // SHIPPING ADDRESS
    // ==========================================

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;


    // ==========================================
    // DEFAULT CONSTRUCTOR
    // ==========================================

    public Order() {
        super();
    }


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Order(
            Long id,
            LocalDateTime orderDate,
            Double totalAmount,
            String status,
            User user,
            List<OrderItem> orderItems,
            Address shippingAddress) {

        super();

        this.id = id;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.user = user;
        this.orderItems = orderItems;
        this.shippingAddress = shippingAddress;
    }


    // ==========================================
    // GET ID
    // ==========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    // ==========================================
    // ORDER DATE
    // ==========================================

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }


    // ==========================================
    // TOTAL AMOUNT
    // ==========================================

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }


    // ==========================================
    // STATUS
    // ==========================================

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    // ==========================================
    // USER
    // ==========================================

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    // ==========================================
    // ORDER ITEMS
    // ==========================================

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }


    // ==========================================
    // SHIPPING ADDRESS
    // ==========================================

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}