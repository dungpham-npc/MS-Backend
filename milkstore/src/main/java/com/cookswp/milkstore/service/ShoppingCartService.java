package com.cookswp.milkstore.service;


import com.cookswp.milkstore.pojo.entities.ShoppingCart;
import com.cookswp.milkstore.pojo.entities.ShoppingCartItem;
import com.cookswp.milkstore.repository.ShoppingCartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public List<ShoppingCart> getAllCarts() {
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart getCartById (int id) {
        return shoppingCartRepository.findById(id).orElse(null);
    }

    public ShoppingCart saveCart (ShoppingCart cart) {
        return shoppingCartRepository.save(cart);
    }

    public void deleteCart (int id) {
        shoppingCartRepository.deleteById(id);
    }

    //Add item to cart
    public void addItemToCart (ShoppingCart cart, ShoppingCartItem item) {
        List<ShoppingCartItem> items = cart.getItems();
        items.add(item);
        cart.setItems(items);
        shoppingCartRepository.save(cart);
    }

    //Remove item from cart
    public void removeItemFromCart (ShoppingCart cart, ShoppingCartItem item){
        List<ShoppingCartItem> items = cart.getItems();
        items.remove(item);
        cart.setItems(items);
        shoppingCartRepository.save(cart);
    }

//    //Calculate total price of item from cart
//    //Lỗi con cặc gì đéo biết fix :))))
//    public double calculateTotalPrice (ShoppingCart cart) {
//        double totalPrice = 0;
//        List<ShoppingCartItem> items = cart.getItems();
//        for(ShoppingCartItem item : items) {
//            totalPrice += item.getShoppingCart().getItems().get;
//        }
//        return totalPrice;
//    }

}
