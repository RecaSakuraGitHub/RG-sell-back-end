package com.recasakura.sellbackend.model.cart;

public class CartAddRequest {
    private String name;
    private Integer quantity;

    public CartAddRequest() {}

    public String getName() { return this.name; }
    public Integer getQuantity() { return this.quantity; }

    public void setName(String name) {
        this.name = name;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
