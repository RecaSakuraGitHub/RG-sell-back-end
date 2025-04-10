package com.recasakura.sellbackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recasakura.sellbackend.model.cart.CartAddRequest;
import com.recasakura.sellbackend.model.cart.CartItemResponse;
import com.recasakura.sellbackend.service.CartService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartAddRequest request, HttpSession session) {
        this.cartService.addToCart(request, session);
        return ResponseEntity.ok(Map.of("message", "product add to cart success"));
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCart(HttpSession session) {
        return ResponseEntity.ok(this.cartService.getCart(session));
    }
}
