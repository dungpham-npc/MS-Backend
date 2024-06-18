package com.cookswp.milkstore.repository.product;

import com.cookswp.milkstore.pojo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.status = TRUE")
    List<Product> getAll();

    @Query("SELECT p FROM Product p WHERE p.status = TRUE AND p.productID =:id")
    Product getProductById(@Param("id") int id);

    @Query("SELECT p FROM Product p WHERE p.status = TRUE AND p.productName =:value")
    List<Product> searchProduct(@Param("value") String value);

    boolean existsByCategoryID(int categoryID);

    boolean existsByName(String name);

}

