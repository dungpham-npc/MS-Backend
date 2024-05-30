package com.cookswp.milkstore.service.product;

import com.cookswp.milkstore.pojo.entities.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductService {

    Product createProduct(Product product);

    Product updateProduct(int productID, Product product);

    void deleteProduct(int id);

    Product getProductById(int id);

    List<Product> getAllProducts();

}
