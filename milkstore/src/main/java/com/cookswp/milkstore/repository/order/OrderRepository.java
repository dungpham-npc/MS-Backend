package com.cookswp.milkstore.repository.order;

import com.cookswp.milkstore.pojo.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository <Order, String> {

    Optional<Order> findById(String id);

}