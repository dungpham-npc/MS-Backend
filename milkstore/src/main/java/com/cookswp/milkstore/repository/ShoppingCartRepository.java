package com.cookswp.milkstore.repository;

import com.cookswp.milkstore.pojo.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    Optional<ShoppingCart> findByUserId(int userId);
    Optional<ShoppingCart> findByIdAndUserId(int id, int userId);

}