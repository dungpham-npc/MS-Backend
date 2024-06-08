//package com.cookswp.milkstore.api;
//
//
//import com.cookswp.milkstore.pojo.entities.ShoppingCart;
//import com.cookswp.milkstore.pojo.entities.ShoppingCartItem;
//import com.cookswp.milkstore.service.ShoppingCartService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/carts")
//
//public class CartController {
//    @Autowired
//    private ShoppingCartService shoppingCartService;
//
//    @GetMapping
//    public List<ShoppingCart> getAllCarts() {
//        return shoppingCartService.getAllCarts();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ShoppingCart> getCartById (@PathVariable int id) {
//        ShoppingCart cart = shoppingCartService.getCartById(id);
//        if (cart != null) {
//            return ResponseEntity.ok(cart);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<ShoppingCart> createCart(@RequestBody ShoppingCart cart) {
//        ShoppingCart saveCart = shoppingCartService.saveCart(cart);
//        return ResponseEntity.ok(saveCart);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ShoppingCart> updateCart(@PathVariable int id, @RequestBody ShoppingCart cartDetails) {
//        ShoppingCart cart = shoppingCartService.getCartById(id);
//        if (cart != null) {
//            cart.setUserId(cartDetails.getUserId());
//            cart.setItems(cartDetails.getItems());
//            ShoppingCart updatedCart = shoppingCartService.saveCart(cart);
//            return ResponseEntity.ok(updatedCart);
//
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCart(@PathVariable int id) {
//            ShoppingCart cart = shoppingCartService.getCartById(id);
//            if (cart != null) {
//                shoppingCartService.deleteCart(id);
//                return ResponseEntity.ok().build();
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        }
//
//    @PostMapping("/{cartId}/items")
//    public ResponseEntity<ShoppingCart> addItemToCart (@PathVariable int cartId, @RequestBody ShoppingCartItem item) {
//            ShoppingCart cart = shoppingCartService.getCartById(cartId);
//            if (cart != null) {
//                shoppingCartService.addItemToCart(cart, item);
//                return ResponseEntity.ok(cart);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//    }
//
//    @DeleteMapping("/{cartId}/items/{itemId}")
//    public ResponseEntity<ShoppingCart> removeItemFromCart(@PathVariable int cartId, @PathVariable int itemId) {
//            ShoppingCart cart = shoppingCartService.getCartById(cartId);
//            if (cart != null) {
//                ShoppingCartItem itemRemove = cart.getItems().stream().filter(item -> item.getId() == itemId).findFirst().orElse(null);
//                if (itemRemove != null) {
//                    shoppingCartService.removeItemFromCart(cart, itemRemove);
//                    return ResponseEntity.ok(cart);
//
//                } else {
//                    return ResponseEntity.notFound().build();
//                }
//            } else {
//                    return ResponseEntity.notFound().build();
//                }
//            }
//    }
//
//
