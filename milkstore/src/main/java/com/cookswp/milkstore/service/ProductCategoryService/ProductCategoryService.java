package com.cookswp.milkstore.service.ProductCategoryService;

import com.cookswp.milkstore.model.ProductCategoryModel.ProductCategory;
import com.cookswp.milkstore.repository.ProductCategoryRepository.ProductCatergoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService implements IProductCategoryService{

    @Autowired
    private ProductCatergoryRepository productCatergoryRepository;

    @Override
    public ProductCategory createProductCategory(ProductCategory productCategory) {
        return productCatergoryRepository.save(productCategory);
    }
}
