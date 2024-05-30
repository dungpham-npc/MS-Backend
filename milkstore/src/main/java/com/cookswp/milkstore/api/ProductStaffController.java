package com.cookswp.milkstore.api;

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
    private ProductService milkProductService;

    @Autowired
    private ProductCategoryService productCategoryService;


    @PostMapping("/create-category")
    public ResponseData<ProductCategory> createProductCategory(@RequestBody ProductCategory productCategory) {
        return new ResponseData<>(HttpStatus.CREATED.value(),
                "New product category create successfully",
                productCategoryService.createProductCategory(productCategory));
    }

    //Create Product
    @PostMapping("/create-product")
    public ResponseData<Product> createMilkProduct(@RequestBody Product product) {
        return new ResponseData<>(HttpStatus.CREATED.value(),
                "New Milk Product create successfully",
                milkProductService.createProduct(product));
    }


    //Delete Product
    @DeleteMapping("/delete-product/{userID}")
    public void deleteMilkProduct(@RequestParam int userID) {
        milkProductService.deleteProduct(userID);
    }

    //Update Product


    //Get all
    @GetMapping("/get-product")
    public ResponseData<List<Product>> getMilkProduct() {
        return new ResponseData<>(
                HttpStatus.OK.value(),
                "List Product",
                milkProductService.getAllProducts()
        );
    }


}
