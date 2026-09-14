package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ==========================================
    // USER
    // ==========================================

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    // ==========================================
    // PRODUCT
    // ==========================================

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    // ==========================================
    // QUANTITY
    // ==========================================

    @Column(nullable = false)
    private Integer quantity;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Cart() {
    }


    // ==========================================
    // GETTERS / SETTERS
    // ==========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}