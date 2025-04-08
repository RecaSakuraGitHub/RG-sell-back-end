package com.recasakura.sellbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recasakura.sellbackend.model.product.*;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    List<ProductProjection> findAllBy();
    boolean existsByName(String name);
}
