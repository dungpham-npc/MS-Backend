package com.cookswp.milkstore.service.product;

import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.ProductModel.ProductDTO;
import com.cookswp.milkstore.pojo.entities.Product;
import com.cookswp.milkstore.repository.post.PostRepository;
import com.cookswp.milkstore.repository.product.ProductRepository;
import com.cookswp.milkstore.service.firebase.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final PostRepository postRepository;
    private final FirebaseService firebaseService;
    private final Product product = new Product();

    @Autowired
    public ProductService(ProductRepository productRepository, PostRepository postRepository, FirebaseService firebaseService) {
        this.productRepository = productRepository;
        this.postRepository = postRepository;
        this.firebaseService = firebaseService;
    }

    @Override
    public Product createProduct(ProductDTO productRequest, MultipartFile productImageFile) {
        String imageURL = firebaseService.upload(productImageFile);
        validProductRequest(productRequest);
        Product product = Product.builder()
                .categoryID(productRequest.getCategoryID())
                .postID(productRequest.getPostID())
                .productName(productRequest.getProductName())
                .productDescription(productRequest.getProductDescription())
                .productImage(imageURL)
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .manuDate(productRequest.getManuDate())
                .expiDate(productRequest.getExpiDate())
                .status(true)
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(int productID, ProductDTO productRequest, MultipartFile productImage) {
        Product product = productRepository.getProductById(productID);
        if (product == null) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        // Upload ảnh lên và lấy URL mới
        String imageURL = firebaseService.upload(productImage);

        // Cập nhật thông tin của sản phẩm
        product.setCategoryID(productRequest.getCategoryID());
        product.setPostID(productRequest.getPostID());
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());
        product.setProductImage(imageURL);
        product.setManuDate(productRequest.getManuDate());
        product.setExpiDate(productRequest.getExpiDate());
        product.setStatus(true); // Giả sử bạn muốn luôn set status là true khi cập nhật

        // Lưu lại vào cơ sở dữ liệu
        return productRepository.save(product);
    }


    @Override
    public void deleteProduct(int id) {
        Product product = productRepository.getProductById(id);
        if (product == null) throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        product.setStatus(false);
        productRepository.save(product);
    }

    @Override
    public Product getProductById(int id) {
        Product product = productRepository.getProductById(id);
        if (product == null) throw new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> list = productRepository.getAll();
        if (list == null) throw new AppException(ErrorCode.PRODUCT_LIST_NOT_FOUND);
        return list;
    }

    @Override
    public List<Product> searchProduct(String value) {
        List<Product> searchList = productRepository.searchProduct(value);
        if (searchList == null) throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        return searchList;
    }

    private void validProductRequest(ProductDTO productRequest) {
        if (!productRepository.existsByCategoryID(productRequest.getCategoryID())) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }
        if (!postRepository.existsById(productRequest.getPostID())) {
            throw new AppException(ErrorCode.POST_ID_NOT_FOUND);
        }
        if (productRequest.getPrice() == null || productRequest.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new AppException(ErrorCode.INVALID_PRICE);
        }
        if (productRequest.getProductDescription() == null || productRequest.getProductDescription().isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_DESCRIPTION_IS_NULL);
        }
        if (productRepository.existsByProductName(productRequest.getProductName())) {
            throw new AppException(ErrorCode.PRODUCT_NAME_EXISTS);
        }
        boolean checkDate = product.dateBefore(productRequest.getManuDate(), productRequest.getExpiDate());
        if (!checkDate) {
            throw new AppException(ErrorCode.MANU_DATE_CAN_NOT_BEFORE_EXPI_DATE);
        }
//        String image = productRequest.getProductImage().toLowerCase();
//        if (!image.matches(".*\\.(jpeg|png|jpg)$")) {
//            throw new AppException(ErrorCode.PRODUCT_IMAGE_INVALID);
//        }
        if (productRequest.getQuantity() < 0) {
            throw new AppException(ErrorCode.PRODUCT_QUANTITY_INVALID);
        }
    }


    //        product.setCategoryID(productRequest.getCategoryID());
//        product.setProductImage(imageURL);
//        product.setProductName(productRequest.getProductName());
//        product.setProductDescription(productRequest.getProductDescription());
//        product.setQuantity(productRequest.getQuantity());
//        product.setPostID(productRequest.getPostID());
//        product.setManuDate(productRequest.getManuDate());
//        product.setExpiDate(productRequest.getExpiDate());
//        product.setPrice(productRequest.getPrice());
}
