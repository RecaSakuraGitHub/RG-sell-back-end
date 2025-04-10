package com.recasakura.sellbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.recasakura.sellbackend.exception.ProductAlreadyExistsException;
import com.recasakura.sellbackend.exception.ProductNotFoundException;
import com.recasakura.sellbackend.exception.UnauthorizedException;
import com.recasakura.sellbackend.model.product.*;
import com.recasakura.sellbackend.model.user.User;
import com.recasakura.sellbackend.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void checkAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals("ADMIN")) {
            throw new UnauthorizedException();
        }
    }

    public ProductResponse createProduct(ProductRequest request) {
        if (existsName(request.getName())) {
            throw new ProductAlreadyExistsException();
        }
        Product product = new Product(request.getName(), request.getPrice());
        ProductResponse response = new ProductResponse(this.productRepository.save(product));
        return response;
    }

    public void deleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }

    public Product getProduct(String name) {
        Product response = this.productRepository.findByName(name).orElseThrow(
            () -> new ProductNotFoundException()
        );
        return response;
    }

    public Product getProduct(Long id) {
        Product response = this.productRepository.findById(id).orElseThrow(
            () -> new ProductNotFoundException()
        );
        return response;
    }

    public List<ProductProjection> getProducts() {
        return this.productRepository.findAllBy();
    }

    public boolean existsName(String name) {
        return this.productRepository.existsByName(name);
    }
}
