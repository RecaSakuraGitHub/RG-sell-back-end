package com.recasakura.sellbackend.model.cart;

import java.math.BigDecimal;

public class CartItemResponse {
    private String name;
    private Integer quantity;
    private BigDecimal price;

    public CartItemResponse() {}
    public CartItemResponse(String name, Integer quantity, BigDecimal price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() { return this.name; }
    public Integer getQuantity() { return this.quantity; }
    public BigDecimal getPrice() { return this.price; }

    public void setName(String name) {
        this.name = name;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
