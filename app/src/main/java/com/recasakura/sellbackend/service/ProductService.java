package com.recasakura.sellbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.recasakura.sellbackend.exception.ProductAlreadyExistsException;
import com.recasakura.sellbackend.exception.ProductNotFoundException;
import com.recasakura.sellbackend.model.product.*;
import com.recasakura.sellbackend.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest request) {
        if (existsName(request.getName())) {
            throw new ProductAlreadyExistsException();
        }
        Product product = new Product(request.getName(), request.getPrice());
        ProductResponse response = new ProductResponse(this.productRepository.save(product));
        return response;
    }

    public ProductResponse getProduct(Long id) {
        ProductResponse response = new ProductResponse(this.productRepository.findById(id).orElseThrow(
            () -> new ProductNotFoundException()
        ));
        return response;
    }
    public List<ProductProjection> getProducts() {
        return this.productRepository.findAllBy();
    }

    public boolean existsName(String name) {
        return this.productRepository.existsByName(name);
    }
}
