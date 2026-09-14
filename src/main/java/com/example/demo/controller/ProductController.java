package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;


    // =====================================================
    // ADD PRODUCT
    // =====================================================

    @PostMapping(
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Product saveProduct(

            @RequestParam String name,

            @RequestParam String description,

            @RequestParam Double price,

            @RequestParam Integer quantity,

            @RequestParam(required = false)
            Long categoryId,

            @RequestParam(required = false)
            MultipartFile image,

            @RequestParam(required = false)
            String specifications) {

        try {

            Map<String, String> specificationMap = null;

            if (specifications != null
                    && !specifications.trim().isEmpty()) {

                specificationMap =
                        objectMapper.readValue(
                                specifications,
                                new TypeReference<Map<String, String>>() {}
                        );
            }

            return productService.saveProduct(
                    name,
                    description,
                    price,
                    quantity,
                    categoryId,
                    image,
                    specificationMap
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Invalid specifications format",
                    e
            );
        }
    }


    // =====================================================
    // GET ALL PRODUCTS
    // =====================================================

    @GetMapping
    public List<Product> getAllProducts() {

        return productService.getAllProducts();
    }


    // =====================================================
    // GET PRODUCT BY ID
    // =====================================================

    @GetMapping("/{id}")
    public Product getProductById(
            @PathVariable Long id) {

        return productService.getProductById(id);
    }


    // =====================================================
    // UPDATE PRODUCT
    // =====================================================

    @PutMapping(
        value = "/{id}",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Product updateProduct(

            @PathVariable Long id,

            @RequestParam String name,

            @RequestParam String description,

            @RequestParam Double price,

            @RequestParam Integer quantity,

            @RequestParam(required = false)
            Long categoryId,

            @RequestParam(required = false)
            MultipartFile image,

            @RequestParam(required = false)
            String specifications) {

        try {

            Map<String, String> specificationMap = null;

            if (specifications != null
                    && !specifications.trim().isEmpty()) {

                specificationMap =
                        objectMapper.readValue(
                                specifications,
                                new TypeReference<Map<String, String>>() {}
                        );
            }

            return productService.updateProduct(
                    id,
                    name,
                    description,
                    price,
                    quantity,
                    categoryId,
                    image,
                    specificationMap
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Invalid specifications format",
                    e
            );
        }
    }


    // =====================================================
    // DELETE PRODUCT
    // =====================================================

    @DeleteMapping("/{id}")
    public String deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return "Product deleted successfully";
    }
}