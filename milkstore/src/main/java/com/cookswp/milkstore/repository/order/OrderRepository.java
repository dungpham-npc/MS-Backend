package com.cookswp.milkstore.repository.order;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.pojo.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository <Order, String> {

    Optional<Order> findById(String id);

    List<Order> findByUserId (int userId);

    @Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.cart WHERE o.id = :orderID")
    Order findOrderWithItemsById(@Param("orderID") String orderID);




}