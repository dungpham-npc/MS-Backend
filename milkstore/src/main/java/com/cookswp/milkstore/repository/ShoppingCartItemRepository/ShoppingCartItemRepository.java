package com.cookswp.milkstore.repository.ShoppingCartItemRepository;

import com.cookswp.milkstore.pojo.entities.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Integer> {
}
