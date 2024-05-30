package com.cookswp.milkstore.service.product;

import com.cookswp.milkstore.pojo.entities.Product;
import com.cookswp.milkstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(int productID, Product product) {
        Optional<Product> existingProduct = productRepository.findById(productID);
        if(existingProduct.isPresent()){
            Product updatedProduct = existingProduct.get();
            updatedProduct.setProductName(product.getProductName());
            updatedProduct.setProductImage(product.getProductImage());
            updatedProduct.setProductDescription(product.getProductDescription());
            updatedProduct.setQuantity(product.getQuantity());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setCategoryID(product.getCategoryID());
            return productRepository.save(updatedProduct);
        } else {
            throw new RuntimeException("Not found product with id: " + productID);
        }
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found")
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
