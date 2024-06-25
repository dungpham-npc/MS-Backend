package com.cookswp.milkstore.repository.shoppingCartItem;

import com.cookswp.milkstore.pojo.entities.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Integer> {
}
