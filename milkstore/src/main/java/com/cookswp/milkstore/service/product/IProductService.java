package com.cookswp.milkstore.service.product;

import com.cookswp.milkstore.pojo.dtos.ProductModel.ProductDTO;
import com.cookswp.milkstore.pojo.entities.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductService {

    Product createProduct(ProductDTO product);

    Product updateProduct(int productID, ProductDTO product);

    void deleteProduct(int id);

    Product getProductById(int id);

    List<Product> getAllProducts();

    List<Product> searchProduct(String keyword);

}
