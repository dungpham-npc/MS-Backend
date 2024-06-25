package com.cookswp.milkstore.service.productCategory;

import com.cookswp.milkstore.pojo.dtos.ProductCategoryModel.ProductCategoryDTO;
import com.cookswp.milkstore.pojo.entities.ProductCategory;

public interface IProductCategoryService {

    ProductCategory createProductCategory(ProductCategoryDTO requestCategory);

    ProductCategory updateProductCategory(int id, ProductCategoryDTO requestCategory);

    ProductCategory deleteProductCategory(ProductCategoryDTO requestCategory);

    ProductCategory findProductCategory(ProductCategoryDTO requestCategory);
}

