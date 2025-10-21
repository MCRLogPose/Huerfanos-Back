package com.bakerysystem.products.repository;

import com.bakerysystem.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.sku) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.category.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Product> search(@Param("query") String query);

    List<Product> findByCategoryId(Long categoryId);
}