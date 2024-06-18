package com.cookswp.milkstore.service.product;

import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.ProductModel.ProductDTO;
import com.cookswp.milkstore.pojo.entities.Product;
import com.cookswp.milkstore.repository.post.PostRepository;
import com.cookswp.milkstore.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final PostRepository postRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, PostRepository postRepository) {
        this.productRepository = productRepository;
        this.postRepository = postRepository;
    }

    private void validProductRequest(ProductDTO productRequest) {
        if(!productRepository.existsByCategoryID(productRequest.getCategoryID())){
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }
        if(!postRepository.existsById(productRequest.getPostID())){
            throw new AppException(ErrorCode.POST_ID_NOT_FOUND);
        }
        if(productRequest.getPrice() == null || productRequest.getPrice().compareTo(BigDecimal.ZERO) == 0){
            throw new AppException(ErrorCode.INVALID_PRICE);
        }
        if(productRequest.getProductDescription() == null || productRequest.getProductDescription().isEmpty()){
            throw new AppException(ErrorCode.PRODUCT_DESCRIPTION_IS_NULL);
        }
        if(!productRepository.existsByName(productRequest.getProductName())){
            throw new AppException(ErrorCode.PRODUCT_NAME_EXISTS);
        }

    }

    private void validProductImage(ProductDTO productRequest) {

    }

    @Override
    public Product createProduct(ProductDTO productRequest) {
        validProductRequest(productRequest);
        Product productEntity = Product.builder()
                .categoryID(productRequest.getCategoryID())
                .postID(productRequest.getPostID())
                .price(productRequest.getPrice())
                .productDescription(productRequest.getProductDescription())
                .productName(productRequest.getProductName())
                .productImage(productRequest.getProductImage())
                .quantity(productRequest.getQuantity())
                .build();
        return productRepository.save(productEntity);
    }



    @Override
    public Product updateProduct(int productID, ProductDTO productRequest) {
        Product existingProduct = productRepository.getProductById(productID);
        if (existingProduct != null) {
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
        if (productEntity != null) {
            productEntity.setStatus(false);
            productRepository.save(productEntity);
        } else {
            throw new RuntimeException("Not found product with id: " + id);
        }
    }

    @Override
    public Product getProductById(int id) {
        Product findProduct = productRepository.getProductById(id);
        if (findProduct != null) {
            return findProduct;
        } else {
            throw new RuntimeException("Not found product with id: " + id);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> list = productRepository.getAll();
        if (list != null) {
            return list;
        } else {
            throw new RuntimeException("Not found any products");
        }
    }

    @Override
    public List<Product> searchProduct(String value) {
        List<Product> searchList = productRepository.searchProduct(value);
        if (searchList != null) {
            return searchList;
        } else {
            throw new RuntimeException("Not found any products");
        }
    }
}
