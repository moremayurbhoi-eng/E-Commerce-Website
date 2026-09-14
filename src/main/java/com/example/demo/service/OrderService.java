package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Address;
import com.example.demo.entity.Order;

public interface OrderService {

    Order placeOrder(String email, Address address);

    List<Order> getOrdersByUserEmail(String email);

    List<Order> getAllOrders();

    Order getOrderById(String email, Long orderId);

    Order updateOrderStatus(Long orderId, String status);
}