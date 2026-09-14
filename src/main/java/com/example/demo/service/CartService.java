package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Cart;

public interface CartService {

    Cart addToCart(
            String email,
            Long productId,
            Integer quantity
    );

    List<Cart> getCartByUserEmail(
            String email
    );

    Cart updateCart(
            String email,
            Long cartId,
            Integer quantity
    );

    void removeFromCart(
            String email,
            Long cartId
    );
}