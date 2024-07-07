package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.ProductCategoryModel.ProductCategoryDTO;
import com.cookswp.milkstore.pojo.dtos.ProductModel.ProductDTO;
import com.cookswp.milkstore.pojo.entities.Post;
import com.cookswp.milkstore.pojo.entities.ProductCategory;
import com.cookswp.milkstore.pojo.entities.Product;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.product.ProductService;
import com.cookswp.milkstore.service.productCategory.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductStaffController {

    private final ProductService productService;

    private final ProductCategoryService productCategoryService;

    @PostMapping("/category")
    public ResponseData<ProductCategory> createProductCategory(@RequestBody ProductCategoryDTO productCategoryRequest) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "New product category create successfully", productCategoryService.createProductCategory(productCategoryRequest));
    }

    @GetMapping("/list-category")
    public ResponseData<List<ProductCategory>> listProductCategory() {
        return new ResponseData<>(HttpStatus.OK.value(), "List of product category", productCategoryService.findAllProductCategories());
    }

    @PostMapping("/")
    public ResponseData<Product> createProduct(@ModelAttribute ProductDTO productRequest, @RequestParam("productImage") MultipartFile productImage) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "New Milk Product create successfully", productService.createProduct(productRequest, productImage));
    }

    //Delete Product
    @PatchMapping("/{ID}/delete")
    public ResponseData<Post> deleteProduct(@PathVariable int ID) {
        productService.deleteProduct(ID);
        return new ResponseData<>(HttpStatus.OK.value(), "Product deleted successfully", null);
    }

    //Update Product
    @PatchMapping("/{productId}")
    public ResponseData<Product> updateProduct(@PathVariable int productId, @ModelAttribute ProductDTO productRequest, @RequestParam("productImage") MultipartFile productImage) {
        return new ResponseData<>(HttpStatus.OK.value(), "Product update successfully", productService.updateProduct(productId, productRequest, productImage));
    }

    //Get all
    @GetMapping
    // @PreAuthorize("hasAuthority('PRODUCT_STAFF')")
    public ResponseData<List<Product>> getProduct() {
        return new ResponseData<>(HttpStatus.OK.value(), "List Product", productService.getAllProducts());
    }

    //get product by id
    @GetMapping("/{ID}")
    public ResponseData<Product> getProductByID(@PathVariable int ID) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get product by ID: " + ID, productService.getProductById(ID));
    }

    //              /search-product?value=abs
    @GetMapping("/search")
    //@PreAuthorize("hasAuthority('PRODUCT_STAFF')")
    public ResponseData<List<Product>> searchProduct(@RequestParam(value = "value") String value) {
        return new ResponseData<>(HttpStatus.OK.value(), "Search product: " + value, productService.searchProduct(value));
    }
    //


}
