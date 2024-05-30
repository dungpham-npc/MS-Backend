package com.cookswp.milkstore.controller;

import com.cookswp.milkstore.model.ProductCategoryModel.ProductCategory;
import com.cookswp.milkstore.model.ProductModel.MilkProduct;
import com.cookswp.milkstore.service.MilkProductService.MilkProductService;
import com.cookswp.milkstore.service.ProductCategoryService.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-staff")
public class ProductStaffController {

    @Autowired
    private MilkProductService milkProductService;

    @Autowired
    private ProductCategoryService productCategoryService;


    @PostMapping("/create-category")
    public ResponseEntity<ProductCategory> createProductCategory(@RequestBody ProductCategory productCategory) {
        return new ResponseEntity<>(productCategoryService.createProductCategory(productCategory), HttpStatus.CREATED);
    }

    //Create Product
    @PostMapping("/create-product")
    public ResponseEntity<MilkProduct> createMilkProduct(@RequestBody MilkProduct milkProduct) {
        return new ResponseEntity<>(milkProductService.createMilkProduct(milkProduct), HttpStatus.CREATED);
    }


    //Delete Product
    @DeleteMapping("/delete-product/{userID}")
    public void deleteMilkProduct(@RequestParam int userID) {
        milkProductService.deleteMilkProduct(userID);
    }

    //Update Product


    //Get all
    @GetMapping("/get-product")
    public ResponseEntity<List<MilkProduct>> getMilkProduct() {
        return new ResponseEntity<>(milkProductService.getAllMilkProducts(), HttpStatus.ACCEPTED);
    }


}
