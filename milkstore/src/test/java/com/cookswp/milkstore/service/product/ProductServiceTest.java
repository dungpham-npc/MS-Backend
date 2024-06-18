package com.cookswp.milkstore.service.product;

import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.pojo.dtos.ProductModel.ProductDTO;
import com.cookswp.milkstore.pojo.entities.Post;
import com.cookswp.milkstore.pojo.entities.Product;
import com.cookswp.milkstore.repository.post.PostRepository;
import com.cookswp.milkstore.repository.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PostRepository postRepository;

    @Test
    void testCreateProduct_ProductNameMustBeUnique() {
        // Mocking the behavior of productRepository
        when(productRepository.existsByProductName("name")).thenReturn(true);
        // Expect an AppException to be thrown with the appropriate message
        AppException exception = assertThrows(AppException.class, () -> {
            productService.createProduct(ProductDTO.builder()
                    .productName("name")
                    .productDescription("description")
                    .quantity(10)
                    .postID(1)
                    .categoryID(1)
                    .price(BigDecimal.valueOf(100))
                    .productImage("image.jpg")
                    .build());
        });

        // Assert that the exception message is as expected
        assertEquals("Product name already exists", exception.getMessage());
    }

}