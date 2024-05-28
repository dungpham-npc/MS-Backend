package com.cookswp.milkstore.service.MilkProductService;

import com.cookswp.milkstore.model.ProductModel.MilkProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMilkProductService {

    MilkProduct createMilkProduct(MilkProduct milkProduct);

    MilkProduct updateMilkProduct(int productID, MilkProduct milkProduct);

    void deleteMilkProduct(int id);

    List<MilkProduct> getAllMilkProducts();

}
