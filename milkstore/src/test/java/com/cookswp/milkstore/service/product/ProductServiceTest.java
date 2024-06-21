package com.cookswp.milkstore.service.product;

import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.ProductModel.ProductDTO;
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

        assertEquals("Product name already exists in the system", exception.getMessage());
    }

    //Happy case when creating product
    @Test
    void testCreateProduct(){
        Product product = Product.builder()
                .productName("name")
                .productDescription("description")
                .quantity(10)
                .postID(1)
                .categoryID(1)
                .price(BigDecimal.valueOf(100))
                .productImage("image.jpg")
                .build();
        ProductDTO productDTO = ProductDTO.builder()
                .productName("name")
                .productDescription("description")
                .quantity(10)
                .postID(1)
                .categoryID(1)
                .price(BigDecimal.valueOf(100))
                .productImage("image.jpg")
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product createProduct = productService.createProduct(productDTO);

        assertNotNull(createProduct);
        assertEquals(product.getProductName(), createProduct.getProductName());
        assertEquals(product.getProductDescription(), createProduct.getProductDescription());
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

        assertEquals("Product name already exists in the system", exception.getMessage());
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

    //Phần này liên quan tới phần assertEqual khá là khác. Và đây là BadCase
    @Test
    void testEditProduct_ProductMustExistsInTheSystem() {
        // Giả lập rằng sản phẩm với ID 1 không tồn tại trong hệ thống
        when(productRepository.getProductById(1)).thenReturn(null);

        // Kiểm tra rằng ngoại lệ AppException sẽ được ném ra khi cập nhật sản phẩm với ID không tồn tại
        AppException exception = assertThrows(AppException.class, () -> {
//            productService.getProductById(1);
            productService.updateProduct(1, ProductDTO.builder()
                    .productName("name")
                    .productDescription("description")
                    .quantity(10)
                    .postID(1)
                    .categoryID(1)
                    .price(BigDecimal.valueOf(100))
                    .productImage("image.jpg")
                    .build());
        });

        // Kiểm tra msg của ngoại lệ
        //Con mẹ m tại sao ko dùng OOP cho nhanh đi còn ngồi check thống qua string rồi bắt làm gì cho cực z ?????
      assertEquals(ErrorCode.PRODUCT_NOT_FOUND.getMessage(), exception.getMessage());
//        assertEquals("Product ID not exists", exception.getMessage());
    }





}