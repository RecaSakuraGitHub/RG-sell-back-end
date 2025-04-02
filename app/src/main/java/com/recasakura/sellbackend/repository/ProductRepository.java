package com.recasakura.sellbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recasakura.sellbackend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {}
