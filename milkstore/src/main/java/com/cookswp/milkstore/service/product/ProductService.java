package com.cookswp.milkstore.service.product;

import com.cookswp.milkstore.pojo.dtos.ProductModel.ProductDTO;
import com.cookswp.milkstore.pojo.entities.Product;
import com.cookswp.milkstore.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(ProductDTO productRequest) {
        Product productEntity = new Product();
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
    public Product updateProduct(int productID, ProductDTO productRequest) {
        Product existingProduct = productRepository.getProductById(productID);
        if(existingProduct != null){
            existingProduct.setCategoryID(productRequest.getCategoryID());
            existingProduct.setProductImage(productRequest.getProductImage());
            existingProduct.setProductName(productRequest.getProductName());
            existingProduct.setProductDescription(productRequest.getProductDescription());
            existingProduct.setPostID(productRequest.getPostID());
            existingProduct.setQuantity(productRequest.getQuantity());
            existingProduct.setPrice(productRequest.getPrice());
            return productRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Not found product with id: " + productID);
        }
    }


    @Override
    public void deleteProduct(int id) {
        Product productEntity = productRepository.getProductById(id);
        if(productEntity != null){
            productEntity.setStatus(false);
            productRepository.save(productEntity);
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
        } else{
            throw new RuntimeException("Not found any products");
        }
    }
}
