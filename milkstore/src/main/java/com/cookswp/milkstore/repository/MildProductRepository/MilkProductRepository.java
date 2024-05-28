package com.cookswp.milkstore.repository.MildProductRepository;

import com.cookswp.milkstore.model.ProductModel.MilkProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilkProductRepository extends JpaRepository<MilkProduct, Integer> {
}
