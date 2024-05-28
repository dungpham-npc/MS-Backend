package com.cookswp.milkstore.service.MilkProductService;

import com.cookswp.milkstore.model.ProductModel.MilkProduct;
import com.cookswp.milkstore.repository.MildProductRepository.MilkProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilkProductService implements IMilkProductService {

    @Autowired
    private MilkProductRepository milkProductRepository;

    @Override
    public MilkProduct createMilkProduct(MilkProduct milkProduct) {
        return milkProductRepository.save(milkProduct);
    }

    @Override
    public MilkProduct updateMilkProduct(int productID, MilkProduct milkProduct) {
        return null;
    }

    @Override
    public void deleteMilkProduct(int id) {
        milkProductRepository.deleteById(id);
    }

    @Override
    public List<MilkProduct> getAllMilkProducts() {
        return milkProductRepository.findAll();
    }
}
