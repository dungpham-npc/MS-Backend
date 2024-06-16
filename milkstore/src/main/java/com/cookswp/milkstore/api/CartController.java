package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.CartModel.AddToCart;
import com.cookswp.milkstore.pojo.dtos.CartModel.ShowCartModel;
import com.cookswp.milkstore.pojo.dtos.CartModel.UpdateToCart;
import com.cookswp.milkstore.pojo.entities.ShoppingCart;
import com.cookswp.milkstore.service.shoppingcart.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private IShoppingCartService shoppingCartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ShowCartModel>> getCartByUserId(@PathVariable int userId) {
        List<ShowCartModel> carts = shoppingCartService.getCartByUserId(userId);
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<ShoppingCart> addItemToCart(@PathVariable int userId, @RequestBody AddToCart addToCart) {
        ShoppingCart cart = shoppingCartService.addToCart(addToCart, userId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{userId}/carts/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable int userId, @PathVariable int cartId) {
        ShoppingCart cart = shoppingCartService.deleteToCart(cartId, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/carts/{cartId}/items")
    public ResponseEntity<ShoppingCart> updateItemInCart(@PathVariable int userId, @PathVariable int cartId, @RequestBody UpdateToCart updateToCart) {
        ShoppingCart cart = shoppingCartService.updateItem(updateToCart, cartId, userId);
        return ResponseEntity.ok(cart);
    }
}

