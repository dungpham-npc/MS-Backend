package com.cookswp.milkstore.repository.ProductRepository;

import com.cookswp.milkstore.pojo.entities.MilkProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<MilkProduct, Integer> {
}
