package com.recasakura.sellbackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recasakura.sellbackend.model.product.*;
import com.recasakura.sellbackend.service.ProductService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request, HttpSession session) {
        this.productService.checkAdmin(session);
        this.productService.createProduct(request);
        return ResponseEntity.ok(Map.of("message", request.getName() + " created"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, HttpSession session) {
        this.productService.checkAdmin(session);
        String name = this.productService.getProduct(id).getName();
        this.productService.deleteProduct(id);
        return ResponseEntity.ok(Map.of("message", name + " deleted"));
    }

    @GetMapping("/{name}")
    public ResponseEntity<Product> getProduct(@PathVariable String name, HttpSession session) {
        this.productService.checkAdmin(session);
        Product response = this.productService.getProduct(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductProjection>> getProducts() {
        return ResponseEntity.ok(this.productService.getProducts());
    }
}
