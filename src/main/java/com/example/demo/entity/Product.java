package com.example.demo.entity;

import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    // =====================================================
    // ID
    // =====================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // =====================================================
    // NAME
    // =====================================================

    @Column(nullable = false)
    private String name;


    // =====================================================
    // DESCRIPTION
    // =====================================================

    private String description;


    // =====================================================
    // PRICE
    // =====================================================

    @Column(nullable = false)
    private Double price;


    // =====================================================
    // STOCK / QUANTITY
    // =====================================================

    private Integer quantity;


    // =====================================================
    // IMAGE
    // =====================================================

    private String imageUrl;


    // =====================================================
    // PRODUCT SPECIFICATIONS
    // =====================================================

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private Map<String, String> specifications;


    // =====================================================
    // CATEGORY
    // =====================================================

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    // =====================================================
    // DEFAULT CONSTRUCTOR
    // =====================================================

    public Product() {
        super();
    }


    // =====================================================
    // PARAMETERIZED CONSTRUCTOR
    // =====================================================

    public Product(
            Long id,
            String name,
            String description,
            Double price,
            Integer quantity,
            String imageUrl,
            Map<String, String> specifications,
            Category category) {

        super();

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.specifications = specifications;
        this.category = category;
    }


    // =====================================================
    // GET ID
    // =====================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    // =====================================================
    // GET NAME
    // =====================================================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // =====================================================
    // GET DESCRIPTION
    // =====================================================

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    // =====================================================
    // GET PRICE
    // =====================================================

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    // =====================================================
    // GET QUANTITY
    // =====================================================

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    // =====================================================
    // GET IMAGE URL
    // =====================================================

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    // =====================================================
    // GET SPECIFICATIONS
    // =====================================================

    public Map<String, String> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(
            Map<String, String> specifications) {

        this.specifications = specifications;
    }


    // =====================================================
    // GET CATEGORY
    // =====================================================

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}