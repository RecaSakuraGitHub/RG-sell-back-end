package com.recasakura.sellbackend.model.product;

import java.math.BigDecimal;

public class ProductResponse {
    private String name;
    private BigDecimal price;

    public ProductResponse() {}
    public ProductResponse(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public String getName() { return this.name; }
    public BigDecimal getPrice() { return this.price; }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
