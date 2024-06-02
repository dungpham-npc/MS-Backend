package com.cookswp.milkstore.service.productCategory;

import com.cookswp.milkstore.pojo.dtos.ProductCategoryModel.ProductCategoryDTO;
import com.cookswp.milkstore.pojo.entities.ProductCategory;
import com.cookswp.milkstore.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService implements IProductCategoryService{

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory createProductCategory(ProductCategoryDTO productCategoryRequest) {
        ProductCategory productCategoryEntity = new ProductCategory();
        productCategoryEntity.setCategoryName(productCategoryRequest.getCategoryName());
        return productCategoryRepository.save(productCategoryEntity);
    }
}
