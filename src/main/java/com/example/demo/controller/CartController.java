package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Cart;
import com.example.demo.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    // ==========================================
    // ADD TO CART
    // ==========================================

    @PostMapping("/add")
    public Cart addToCart(
            @RequestParam Long productId,
            @RequestParam Integer quantity) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return cartService.addToCart(
                email,
                productId,
                quantity
        );
    }


    // ==========================================
    // GET LOGGED-IN USER CART
    // ==========================================

    @GetMapping
    public List<Cart> getCart() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return cartService.getCartByUserEmail(email);
    }


    // ==========================================
    // UPDATE CART QUANTITY
    // ==========================================

    @PutMapping("/{cartId}")
    public Cart updateCart(
            @PathVariable Long cartId,
            @RequestParam Integer quantity) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return cartService.updateCart(
                email,
                cartId,
                quantity
        );
    }


    // ==========================================
    // DELETE CART ITEM
    // ==========================================

    @DeleteMapping("/{cartId}")
    public String removeFromCart(
            @PathVariable Long cartId) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        cartService.removeFromCart(
                email,
                cartId
        );

        return "Product removed from cart successfully";
    }
}