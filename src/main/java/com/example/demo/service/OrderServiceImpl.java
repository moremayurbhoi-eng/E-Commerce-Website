package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Address;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;

import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            CartRepository cartRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            AddressRepository addressRepository) {

        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
    }


    // =====================================================
    // PLACE ORDER
    // =====================================================

    @Override
    @Transactional
    public Order placeOrder(
            String email,
            Address address) {

        // ==========================================
        // FIND USER
        // ==========================================

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );


        // ==========================================
        // CHECK ADDRESS
        // ==========================================

        if (address == null) {

            throw new RuntimeException(
                    "Shipping address is required"
            );
        }


        // ==========================================
        // VALIDATE ADDRESS
        // ==========================================

        if (address.getFullName() == null ||
                address.getFullName().trim().isEmpty()) {

            throw new RuntimeException(
                    "Full name is required"
            );
        }


        if (address.getPhone() == null ||
                address.getPhone().trim().isEmpty()) {

            throw new RuntimeException(
                    "Phone number is required"
            );
        }


        if (address.getAddress() == null ||
                address.getAddress().trim().isEmpty()) {

            throw new RuntimeException(
                    "Address cannot be empty"
            );
        }


        if (address.getCity() == null ||
                address.getCity().trim().isEmpty()) {

            throw new RuntimeException(
                    "City is required"
            );
        }


        if (address.getState() == null ||
                address.getState().trim().isEmpty()) {

            throw new RuntimeException(
                    "State is required"
            );
        }


        if (address.getPincode() == null ||
                address.getPincode().trim().isEmpty()) {

            throw new RuntimeException(
                    "Pincode is required"
            );
        }


        if (address.getCountry() == null ||
                address.getCountry().trim().isEmpty()) {

            throw new RuntimeException(
                    "Country is required"
            );
        }


        // ==========================================
        // DEBUG
        // ==========================================

        System.out.println(
                "========== ADDRESS =========="
        );

        System.out.println(
                "Full Name : "
                        + address.getFullName()
        );

        System.out.println(
                "Phone     : "
                        + address.getPhone()
        );

        System.out.println(
                "Address   : "
                        + address.getAddress()
        );

        System.out.println(
                "City      : "
                        + address.getCity()
        );

        System.out.println(
                "State     : "
                        + address.getState()
        );

        System.out.println(
                "Pincode   : "
                        + address.getPincode()
        );

        System.out.println(
                "Country   : "
                        + address.getCountry()
        );

        System.out.println(
                "============================="
        );


        // ==========================================
        // GET CART
        // ==========================================

        List<Cart> cartItems =
                cartRepository.findByUser(user);


        if (cartItems == null ||
                cartItems.isEmpty()) {

            throw new RuntimeException(
                    "Cart is empty"
            );
        }


        // ==========================================
        // SAVE ADDRESS
        // ==========================================

        Address savedAddress =
                addressRepository.save(address);


        // ==========================================
        // CREATE ORDER
        // ==========================================

        Order order = new Order();

        order.setUser(user);

        order.setShippingAddress(
                savedAddress
        );

        order.setOrderDate(
                LocalDateTime.now()
        );

        order.setStatus(
                "PLACED"
        );


        // ==========================================
        // ORDER ITEMS
        // ==========================================

        List<OrderItem> orderItems =
                new ArrayList<>();


        double totalAmount = 0.0;


        // ==========================================
        // CART → ORDER ITEMS
        // ==========================================

        for (Cart cart : cartItems) {

            Product product =
                    cart.getProduct();


            if (product == null) {

                throw new RuntimeException(
                        "Product not found in cart"
                );
            }


            int quantity =
                    cart.getQuantity();


            if (quantity <= 0) {

                throw new RuntimeException(
                        "Invalid quantity for product: "
                                + product.getName()
                );
            }


            // ======================================
            // CHECK STOCK
            // ======================================

            if (product.getQuantity()
                    < quantity) {

                throw new RuntimeException(
                        "Not enough stock for product: "
                                + product.getName()
                );
            }


            // ======================================
            // PRICE
            // ======================================

            double price =
                    product.getPrice();


            double itemTotal =
                    price * quantity;


            totalAmount =
                    totalAmount + itemTotal;


            // ======================================
            // CREATE ORDER ITEM
            // ======================================

            OrderItem orderItem =
                    new OrderItem();


            orderItem.setOrder(order);

            orderItem.setProduct(product);

            orderItem.setQuantity(quantity);

            orderItem.setPrice(price);


            orderItems.add(orderItem);


            // ======================================
            // REDUCE STOCK
            // ======================================

            product.setQuantity(
                    product.getQuantity()
                            - quantity
            );


            productRepository.save(product);
        }


        // ==========================================
        // ORDER DETAILS
        // ==========================================

        order.setTotalAmount(
                totalAmount
        );

        order.setOrderItems(
                orderItems
        );


        // ==========================================
        // SAVE ORDER
        // ==========================================

        Order savedOrder =
                orderRepository.save(order);


        // ==========================================
        // CLEAR CART
        // ==========================================

        cartRepository.deleteAll(
                cartItems
        );


        // ==========================================
        // RETURN
        // ==========================================

        return savedOrder;
    }


    // =====================================================
    // GET MY ORDERS
    // =====================================================

    @Override
    public List<Order> getOrdersByUserEmail(
            String email) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        return orderRepository.findByUser(user);
    }


    // =====================================================
    // GET ALL ORDERS
    // =====================================================

    @Override
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }


    // =====================================================
    // GET ORDER BY ID
    // =====================================================

    @Override
    public Order getOrderById(
            String email,
            Long orderId) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        Order order =
                orderRepository
                        .findById(orderId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"
                                )
                        );


        // ==========================================
        // OWNERSHIP CHECK
        // ==========================================

        if (order.getUser() == null ||
                !order.getUser()
                        .getId()
                        .equals(user.getId())) {

            throw new RuntimeException(
                    "You are not allowed to view this order"
            );
        }


        return order;
    }


    // =====================================================
    // UPDATE ORDER STATUS
    // =====================================================

    @Override
    public Order updateOrderStatus(
            Long orderId,
            String status) {

        Order order =
                orderRepository
                        .findById(orderId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"
                                )
                        );


        // ==========================================
        // VALIDATE STATUS
        // ==========================================

        if (status == null ||
                status.trim().isEmpty()) {

            throw new RuntimeException(
                    "Order status is required"
            );
        }


        String newStatus =
                status.trim().toUpperCase();


        // ==========================================
        // ALLOWED STATUS
        // ==========================================

        if (!newStatus.equals("PLACED") &&
                !newStatus.equals("CONFIRMED") &&
                !newStatus.equals("SHIPPED") &&
                !newStatus.equals("DELIVERED") &&
                !newStatus.equals("CANCELLED")) {

            throw new RuntimeException(
                    "Invalid order status. "
                    + "Allowed values: "
                    + "PLACED, CONFIRMED, "
                    + "SHIPPED, DELIVERED, "
                    + "CANCELLED"
            );
        }


        order.setStatus(newStatus);


        return orderRepository.save(order);
    }
}