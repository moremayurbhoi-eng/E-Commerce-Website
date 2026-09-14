package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductServiceIml implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    private final String uploadDirectory =
            "uploads/products";


    // =====================================================
    // ADD PRODUCT
    // =====================================================

    @Override
    public Product saveProduct(

            String name,

            String description,

            Double price,

            Integer quantity,

            Long categoryId,

            MultipartFile image,

            Map<String, String> specifications) {

        Product product = new Product();


        // Basic details

        product.setName(name);

        product.setDescription(description);

        product.setPrice(price);

        product.setQuantity(quantity);


        // Specifications

        product.setSpecifications(
                specifications
        );


        // Category

        if (categoryId != null) {

            Category category =
                    categoryRepository
                    .findById(categoryId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Category not found"
                            )
                    );

            product.setCategory(category);
        }


        // Image

        if (image != null
                && !image.isEmpty()) {

            String imageUrl =
                    saveImage(image);

            product.setImageUrl(imageUrl);
        }


        return productRepository.save(product);
    }


    // =====================================================
    // GET ALL PRODUCTS
    // =====================================================

    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }


    // =====================================================
    // GET PRODUCT BY ID
    // =====================================================

    @Override
    public Product getProductById(Long id) {

        return productRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found"
                        )
                );
    }


    // =====================================================
    // UPDATE PRODUCT
    // =====================================================

    @Override
    public Product updateProduct(

            Long id,

            String name,

            String description,

            Double price,

            Integer quantity,

            Long categoryId,

            MultipartFile image,

            Map<String, String> specifications) {

        Product product =
                productRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found"
                        )
                );


        // Basic details

        product.setName(name);

        product.setDescription(description);

        product.setPrice(price);

        product.setQuantity(quantity);


        // Specifications

        product.setSpecifications(
                specifications
        );


        // Category

        if (categoryId != null) {

            Category category =
                    categoryRepository
                    .findById(categoryId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Category not found"
                            )
                    );

            product.setCategory(category);
        }


        // Image

        if (image != null
                && !image.isEmpty()) {

            String imageUrl =
                    saveImage(image);

            product.setImageUrl(imageUrl);
        }


        return productRepository.save(product);
    }


    // =====================================================
    // DELETE PRODUCT
    // =====================================================

    @Override
    @Transactional
    public void deleteProduct(Long id) {

        Product product =
                productRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found"
                        )
                );


        // Delete related order items

        productRepository
                .deleteOrderItemsByProductId(id);


        // Delete related cart items

        productRepository
                .deleteCartItemsByProductId(id);


        // Delete product

        productRepository.delete(product);
    }


    // =====================================================
    // SAVE IMAGE
    // =====================================================

    private String saveImage(
            MultipartFile image) {

        try {

            Path uploadPath =
                    Paths.get(uploadDirectory);


            if (!Files.exists(uploadPath)) {

                Files.createDirectories(
                        uploadPath
                );
            }


            String originalFilename =
                    image.getOriginalFilename();


            String extension = "";


            if (originalFilename != null
                    && originalFilename.contains(".")) {

                extension =
                        originalFilename.substring(
                                originalFilename
                                .lastIndexOf(".")
                        );
            }


            String filename =
                    UUID.randomUUID()
                    + extension;


            Path filePath =
                    uploadPath.resolve(filename);


            Files.copy(
                    image.getInputStream(),
                    filePath,
                    StandardCopyOption
                            .REPLACE_EXISTING
            );


            return "/uploads/products/"
                    + filename;


        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to save product image",
                    e
            );
        }
    }
}