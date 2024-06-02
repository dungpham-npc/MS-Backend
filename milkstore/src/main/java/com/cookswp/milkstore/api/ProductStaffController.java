package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.ProductCategoryModel.ProductCategoryDTO;
import com.cookswp.milkstore.pojo.dtos.ProductModel.ProductDTO;
import com.cookswp.milkstore.pojo.entities.Post;
import com.cookswp.milkstore.pojo.entities.ProductCategory;
import com.cookswp.milkstore.pojo.entities.Product;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.product.ProductService;
import com.cookswp.milkstore.service.productCategory.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-staff")
public class ProductStaffController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;


    @PostMapping("/create-category")
    public ResponseData<ProductCategory> createProductCategory(@RequestBody ProductCategoryDTO productCategoryRequest) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "New product category create successfully", productCategoryService.createProductCategory(productCategoryRequest));
    }

    //Create Product
    @PostMapping("/create-product")
    public ResponseData<Product> createProduct(@RequestBody ProductDTO productRequest) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "New Milk Product create successfully", productService.createProduct(productRequest));
    }

    //Delete Product
    @PatchMapping("/delete-product/{ID}")
    public ResponseData<Post> deleteProduct(@PathVariable int ID) {
        productService.deleteProduct(ID);
        return new ResponseData<>(HttpStatus.OK.value(), "Product deleted successfully");
    }

    //Update Product
    @PatchMapping("/update-product/{userID}")
    public ResponseData<Product> updateProduct(@PathVariable int userID, @RequestBody ProductDTO productRequest) {
        return new ResponseData<>(HttpStatus.OK.value(), "Product update successfully", productService.updateProduct(userID, productRequest));
    }
    //Get all
    @GetMapping("/get-product")
    public ResponseData<List<Product>> getProduct() {
        return new ResponseData<>(HttpStatus.OK.value(), "List Product", productService.getAllProducts());
    }

    //get product by id
    @GetMapping("/get-product/{ID}")
    public ResponseData<Product> getProductByID(@PathVariable int ID) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get product by ID: " + ID, productService.getProductById(ID));
    }

    @GetMapping("/search-product")
    public ResponseData<List<Product>> searchProduct(@RequestParam(value = "value") String value) {
        return new ResponseData<>(HttpStatus.OK.value(), "Search product: " + value, productService.searchProduct(value));
    }


}
