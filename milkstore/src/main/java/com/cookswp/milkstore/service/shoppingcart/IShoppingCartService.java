package com.cookswp.milkstore.service.shoppingcart;

import com.cookswp.milkstore.pojo.dtos.CartModel.AddToCart;
import com.cookswp.milkstore.pojo.dtos.CartModel.ShowCartModel;
import com.cookswp.milkstore.pojo.dtos.CartModel.UpdateToCart;
import com.cookswp.milkstore.pojo.entities.ShoppingCart;

import java.util.List;

public interface IShoppingCartService {
    List<ShowCartModel> getCartByUserId(int userId);

    ShoppingCart addToCart(AddToCart addToCart, int userId);

    ShoppingCart deleteToCart (int cartId, int userId);

    ShoppingCart updateItem(UpdateToCart updateToCart, int cartId, int userId);

}
