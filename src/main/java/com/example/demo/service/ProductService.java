package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;

public interface ProductService {

    Product saveProduct(
            String name,
            String description,
            Double price,
            Integer quantity,
            Long categoryId,
            MultipartFile image,
            Map<String, String> specifications
    );

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product updateProduct(
            Long id,
            String name,
            String description,
            Double price,
            Integer quantity,
            Long categoryId,
            MultipartFile image,
            Map<String, String> specifications
    );

    void deleteProduct(Long id);
}