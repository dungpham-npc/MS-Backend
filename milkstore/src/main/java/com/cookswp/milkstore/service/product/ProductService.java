package com.cookswp.milkstore.service.product;

import com.cookswp.milkstore.pojo.dtos.ProductModel.ProductDTO;
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
    public Product createProduct(ProductDTO productRequest) {
        Product productEntity = new Product();
        return getProduct(productRequest, productEntity);
    }

    @Override
    public Product updateProduct(int productID, ProductDTO productRequest) {
        Optional<Product> existingProduct = productRepository.findById(productID);
        if(existingProduct.isPresent()){
            Product productEntity = existingProduct.get();
            return getProduct(productRequest, productEntity);
        } else {
            throw new RuntimeException("Not found product with id: " + productID);
        }
    }

    private Product getProduct(ProductDTO productRequest, Product productEntity) {
        productEntity.setCategoryID(productRequest.getCategoryID());
        productEntity.setPostID(productRequest.getPostID());
        productEntity.setProductName(productRequest.getProductName());
        productEntity.setProductDescription(productRequest.getProductDescription());
        productEntity.setProductImage(productRequest.getProductImage());
        productEntity.setQuantity(productRequest.getQuantity());
        productEntity.setPrice(productRequest.getPrice());
        return productRepository.save(productEntity);
    }

    @Override
    public void deleteProduct(int id) {
        Optional<Product> productEntity = productRepository.findById(id);
        if(productEntity.isPresent()){
            Product product = productEntity.get();
            product.setStatus(false);
            productRepository.save(product);
        } else {
            throw new RuntimeException("Not found product with id: " + id);
        }
    }

    @Override
    public Product getProductById(int id) {
       Product findProduct = productRepository.getProductById(id);
       if(findProduct != null){
           return findProduct;
       } else {
           throw new RuntimeException("Not found product with id: " + id);
       }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> list = productRepository.getAll();
        if(list != null){
            return list;
        } else {
            throw new RuntimeException("Not found any products");
        }
    }

    @Override
    public List<Product> searchProduct(String value) {
        List<Product> searchList = productRepository.searchProduct(value);
        if(searchList != null){
            return searchList;
        } else {
            throw new RuntimeException("Not found any product with value: " + value);
        }
    }
}
