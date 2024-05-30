package com.cookswp.milkstore.repository;

import com.cookswp.milkstore.pojo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
