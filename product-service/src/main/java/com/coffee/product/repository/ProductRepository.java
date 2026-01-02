package com.coffee.product.repository;

import com.coffee.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Intermediate Native SQL Query
    @Query(
            value = "SELECT * FROM products WHERE price >= :price",
            nativeQuery = true
    )
    List<Product> findProductsAbovePrice(@Param("price") double price);
}

