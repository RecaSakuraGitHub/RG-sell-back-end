package com.recasakura.sellbackend.model.cart;

import java.math.BigDecimal;

public class CartAddRequest {
    private Long productId;
    private BigDecimal price;

    public CartAddRequest() {}

    public Long getProductId() { return this.productId; }
    public BigDecimal getPrice() { return this.price; }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
