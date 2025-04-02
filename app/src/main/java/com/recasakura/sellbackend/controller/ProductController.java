package com.recasakura.sellbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recasakura.sellbackend.model.Product;
import com.recasakura.sellbackend.repository.ProductRepository;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {
    private final ProductRepository productRepository;
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        this.productRepository.save(product);
        return ResponseEntity.ok("product: " + product.getName() + " is created.");
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProduct() {
        return ResponseEntity.ok(this.productRepository.findAll());
    }
}
