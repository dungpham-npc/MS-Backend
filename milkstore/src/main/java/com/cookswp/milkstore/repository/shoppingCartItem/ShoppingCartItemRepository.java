package com.cookswp.milkstore.repository.shoppingCartItem;

import com.cookswp.milkstore.pojo.entities.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Integer> {

    List<ShoppingCartItem> findById(long orderId);
}
