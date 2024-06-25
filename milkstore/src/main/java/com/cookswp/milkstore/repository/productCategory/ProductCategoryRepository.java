package com.cookswp.milkstore.repository.productCategory;

import com.cookswp.milkstore.pojo.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

}
