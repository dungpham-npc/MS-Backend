package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.CartModel.AddToCartDTO;
import com.cookswp.milkstore.pojo.dtos.CartModel.ShowCartModelDTO;
import com.cookswp.milkstore.pojo.dtos.CartModel.UpdateToCartDTO;
import com.cookswp.milkstore.pojo.entities.ShoppingCart;
import com.cookswp.milkstore.service.shoppingcart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ShowCartModelDTO>> getCartByUserId(@PathVariable int userId) {
        List<ShowCartModelDTO> carts = shoppingCartService.getCartByUserId(userId);
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<ShoppingCart> addItemToCart(@PathVariable int userId, @RequestBody AddToCartDTO addToCartDTO) {
        ShoppingCart cart = shoppingCartService.addToCart(addToCartDTO, userId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/{cartId}/items")
    public ResponseEntity<ShoppingCart> updateItemInCart(@PathVariable int cartId, @RequestParam int userId, @RequestBody UpdateToCartDTO updateToCartDTO) {
        ShoppingCart cart = shoppingCartService.updateItem(updateToCartDTO, cartId, userId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ShoppingCart> deleteCart(@PathVariable int cartId, @RequestParam int userId) {
        ShoppingCart cart = shoppingCartService.deleteToCart(cartId, userId);
        return ResponseEntity.ok(cart);
    }

//    @PostMapping("/{userId}/checkout")
//    public ResponseEntity<String> checkoutCart(@PathVariable int userId) {
//        shoppingCartService.processCheckout(userId);
//        return ResponseEntity.ok("Checkout successful and order placed.");
//    }
}
