package com.recasakura.sellbackend.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.recasakura.sellbackend.exception.ProductNotFoundException;
import com.recasakura.sellbackend.model.cart.CartAddRequest;
import com.recasakura.sellbackend.model.cart.CartItemResponse;
import com.recasakura.sellbackend.model.product.Product;
import com.recasakura.sellbackend.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {
    public final ProductRepository productRepository;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addToCart(CartAddRequest request, HttpSession session) {
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }
        if (!this.productRepository.existsByName(request.getName())) {
            throw new ProductNotFoundException();
        }
        cart.put(request.getName(), cart.getOrDefault(request.getName(), 0) + request.getQuantity());
        session.setAttribute("cart", cart);
    }

    public List<CartItemResponse> getCart(HttpSession session) {
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
        if (cart == null) return List.of(new CartItemResponse("cart is empty", 0, BigDecimal.ZERO));
        return cart.entrySet().stream()
            .map(entry -> {
                Product product = this.productRepository.findByName(entry.getKey()).orElse(null);
                if (product == null) return null;
                BigDecimal productPrice = product.getPrice();
                return new CartItemResponse(entry.getKey(), entry.getValue(), productPrice.multiply(BigDecimal.valueOf(entry.getValue())));
        }).collect(Collectors.toList());
    }
}
