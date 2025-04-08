package com.recasakura.sellbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recasakura.sellbackend.model.product.*;
import com.recasakura.sellbackend.service.ProductService;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProducts(@RequestBody ProductRequest request) {
        ProductResponse response = this.productService.createProduct(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        ProductResponse response = this.productService.getProduct(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<ProductProjection>> getProducts() {
        return ResponseEntity.ok(this.productService.getProducts());
    }
}
