package com.cookswp.milkstore.repository.order;

import com.cookswp.milkstore.pojo.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository <Order, Long> {
}
