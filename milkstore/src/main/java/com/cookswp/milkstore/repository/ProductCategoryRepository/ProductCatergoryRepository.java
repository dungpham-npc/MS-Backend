package com.cookswp.milkstore.repository.ProductCategoryRepository;

import com.cookswp.milkstore.model.ProductCategoryModel.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCatergoryRepository extends JpaRepository<ProductCategory, Integer> {
}
