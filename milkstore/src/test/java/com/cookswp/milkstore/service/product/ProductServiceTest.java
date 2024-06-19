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
        when(productRepository.existsByProductName("name")).thenReturn(true);

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

        assertEquals("Product name must unique", exception.getMessage());
    }

    @Test
    void testCreateProduct_ProductDescriptionIsRequired() {
        AppException exception = assertThrows(AppException.class, () -> {
            productService.createProduct(ProductDTO.builder()
                    .productName("name")
                    .productDescription(null)
                    .productImage("image.jpg")
                    .categoryID(1)
                    .postID(1)
                    .quantity(10)
                    .price(BigDecimal.valueOf(100))
                    .build());
        });

        assertEquals("Product description is required", exception.getMessage());
    }

    @Test
    void testCreateProduct_ProductImageMustBeAsTypeJPEG_JPG() {
        AppException exception = assertThrows(AppException.class, () -> {
            productService.createProduct(ProductDTO.builder()
                    .productName("name")
                    .productDescription("description")
                    .productImage("image.exe")
                    .categoryID(1)
                    .postID(1)
                    .quantity(10)
                    .price(BigDecimal.valueOf(100))
                    .build());
        });

        assertEquals("Invalid product image", exception.getMessage());
    }

    @Test
    void testEditProduct_ProductNameMustBeUnique() {
        when(productRepository.getProductById(1)).thenReturn(Product.builder()
                .productName("name")
                .productDescription("description")
                .productImage("image.png")
                .categoryID(1)
                .postID(1)
                .quantity(10)
                .price(BigDecimal.valueOf(100))
                .build());
        when(productRepository.existsByProductName("name")).thenReturn(true);

        AppException exception = assertThrows(AppException.class, () -> {
            productService.updateProduct(1, ProductDTO.builder()
                    .productName("name")
                    .productDescription("description")
                    .productImage("image.png")
                    .categoryID(1)
                    .postID(1)
                    .quantity(10)
                    .price(BigDecimal.valueOf(100))
                    .build());
        });

        assertEquals("Product name must unique", exception.getMessage());
    }

    @Test
    void testEditProduct_ProductImageMustBeAsType_JPEG_PNG() {
        when(productRepository.getProductById(1)).thenReturn(Product.builder()
                .productName("name")
                .productDescription("description")
                .quantity(10)
                .postID(1)
                .categoryID(1)
                .price(BigDecimal.valueOf(100))
                .productImage("image.jpg")
                .build()
        );

        AppException exception = assertThrows(AppException.class, () -> {
            productService.updateProduct(1, ProductDTO.builder()
                    .productName("name")
                    .productDescription("description")
                    .productImage("image.exe")
                    .categoryID(1)
                    .postID(1)
                    .quantity(10)
                    .price(BigDecimal.valueOf(100))
                    .build());
        });

        assertEquals("Invalid product image", exception.getMessage());
    }

    @Test
    void testAddProductQuantityCanNotBeLessThanZero() {
        AppException exception = assertThrows(AppException.class, () -> {
            productService.createProduct(ProductDTO.builder()
                    .price(BigDecimal.valueOf(100))
                    .quantity(-100)
                    .postID(1)
                    .categoryID(1)
                    .productImage("image.png")
                    .productDescription("description")
                    .productName("name")
                    .build()
            );
        });

        assertEquals("Quantity cannot be less than 0", exception.getMessage());

    }

    @Test
    void testAddProductPriceCanNotBeLessThanZero() {
        AppException exception = assertThrows(AppException.class, () -> {
            productService.createProduct(ProductDTO.builder()
                    .productName("name")
                    .productDescription("description")
                    .productImage("image.png")
                    .categoryID(1)
                    .postID(1)
                    .quantity(10)
                    .price(BigDecimal.valueOf(-10))
                    .build());
        });

        assertEquals("Price cannot be less than 0", exception.getMessage());
    }

}