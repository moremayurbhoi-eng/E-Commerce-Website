package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.OrderRequest;
import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // =====================================================
    // PLACE ORDER
    // USER + ADMIN
    // =====================================================

    @PostMapping("/place")
    public Order placeOrder(
            Authentication authentication,
            @RequestBody OrderRequest request) {

        String email =
                authentication.getName();

        return orderService.placeOrder(
                email,
                request.getAddress()
        );
    }

    // =====================================================
    // GET MY ORDERS
    // USER + ADMIN
    // =====================================================

    @GetMapping("/my")
    public List<Order> getMyOrders(
            Authentication authentication) {

        String email =
                authentication.getName();

        return orderService.getOrdersByUserEmail(
                email
        );
    }

    // =====================================================
    // GET ALL ORDERS
    // ADMIN ONLY
    // =====================================================

    @GetMapping
    public List<Order> getAllOrders() {

        return orderService.getAllOrders();
    }

    // =====================================================
    // GET MY ORDER BY ID
    // USER + ADMIN
    // =====================================================

    @GetMapping("/{orderId}")
    public Order getOrderById(
            Authentication authentication,
            @PathVariable Long orderId) {

        String email =
                authentication.getName();

        return orderService.getOrderById(
                email,
                orderId
        );
    }

    // =====================================================
    // UPDATE ORDER STATUS
    // ADMIN ONLY
    // =====================================================

    @PutMapping("/{orderId}/status")
    public Order updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        return orderService.updateOrderStatus(
                orderId,
                status
        );
    }
}