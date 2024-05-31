package com.cookswp.milkstore.repository.ShoppingCartRepository;

import com.cookswp.milkstore.pojo.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
}

