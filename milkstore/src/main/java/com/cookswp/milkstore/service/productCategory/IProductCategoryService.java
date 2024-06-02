package com.cookswp.milkstore.service.productCategory;

import com.cookswp.milkstore.pojo.dtos.ProductCategoryModel.ProductCategoryDTO;
import com.cookswp.milkstore.pojo.entities.ProductCategory;

public interface IProductCategoryService {

    ProductCategory createProductCategory(ProductCategoryDTO productCategory);

}

