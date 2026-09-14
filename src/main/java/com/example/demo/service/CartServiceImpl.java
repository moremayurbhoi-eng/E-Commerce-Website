package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    // ==========================================
    // ADD TO CART
    // ==========================================

    @Override
    public Cart addToCart(
            String email,
            Long productId,
            Integer quantity) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );


        Product product = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found"
                        )
                );


        if (quantity == null || quantity < 1) {

            throw new RuntimeException(
                    "Quantity must be at least 1"
            );
        }


        // Check stock

        if (product.getQuantity() < quantity) {

            throw new RuntimeException(
                    "Not enough stock available"
            );
        }


        Cart cart = new Cart();

        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(quantity);

        return cartRepository.save(cart);
    }


    // ==========================================
    // GET USER CART
    // ==========================================

    @Override
    public List<Cart> getCartByUserEmail(
            String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        return cartRepository.findByUser(user);
    }


    // ==========================================
    // UPDATE CART
    // ==========================================

    @Override
    public Cart updateCart(
            String email,
            Long cartId,
            Integer quantity) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );


        Cart cart = cartRepository
                .findById(cartId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart item not found"
                        )
                );


        // Check ownership

        if (cart.getUser() == null ||
                !cart.getUser()
                    .getId()
                    .equals(user.getId())) {

            throw new RuntimeException(
                    "You are not allowed to update this cart item"
            );
        }


        if (quantity == null || quantity < 1) {

            throw new RuntimeException(
                    "Quantity must be at least 1"
            );
        }


        Product product = cart.getProduct();


        // Check stock

        if (quantity > product.getQuantity()) {

            throw new RuntimeException(
                    "Not enough stock available"
            );
        }


        cart.setQuantity(quantity);

        return cartRepository.save(cart);
    }


    // ==========================================
    // REMOVE FROM CART
    // ==========================================

    @Override
    public void removeFromCart(
            String email,
            Long cartId) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );


        Cart cart = cartRepository
                .findById(cartId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart item not found"
                        )
                );


        // ======================================
        // IMPORTANT OWNERSHIP CHECK
        // ======================================

        if (cart.getUser() == null ||
                !cart.getUser()
                    .getId()
                    .equals(user.getId())) {

            throw new RuntimeException(
                    "You are not allowed to remove this cart item"
            );
        }


        cartRepository.delete(cart);
    }
}