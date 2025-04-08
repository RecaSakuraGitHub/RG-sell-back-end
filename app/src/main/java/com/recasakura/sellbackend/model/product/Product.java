package com.recasakura.sellbackend.model.product;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
// Table name = products
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10, nullable = false, unique = true)
    private String name;
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    public Product(){}
    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public BigDecimal getPrice() { return this.price; }
    public void setName(String name) {
        this.name = name; 
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
