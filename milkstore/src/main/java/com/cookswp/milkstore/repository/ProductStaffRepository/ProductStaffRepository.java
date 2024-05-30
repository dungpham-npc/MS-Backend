package com.cookswp.milkstore.repository.ProductStaffRepository;

import com.cookswp.milkstore.model.ProductCategoryModel.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStaffRepository extends JpaRepository<ProductCategory, Integer> {
}
