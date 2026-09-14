package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Delete order items referring to this product
    @Modifying
    @Query(
        value = "DELETE FROM order_items WHERE product_id = :productId",
        nativeQuery = true
    )
    void deleteOrderItemsByProductId(
            @Param("productId") Long productId
    );


    // Delete cart items referring to this product
    @Modifying
    @Query(
        value = "DELETE FROM cart WHERE product_id = :productId",
        nativeQuery = true
    )
    void deleteCartItemsByProductId(
            @Param("productId") Long productId
    );
}