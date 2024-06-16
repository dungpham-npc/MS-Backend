package com.cookswp.milkstore.service.shoppingcart;

import com.cookswp.milkstore.pojo.dtos.CartModel.AddToCart;
import com.cookswp.milkstore.pojo.dtos.CartModel.ShowCartModel;
import com.cookswp.milkstore.pojo.dtos.CartModel.UpdateToCart;
import com.cookswp.milkstore.pojo.entities.Product;
import com.cookswp.milkstore.pojo.entities.ShoppingCart;

import com.cookswp.milkstore.pojo.entities.ShoppingCartItem;
//import com.cookswp.milkstore.repository.ProductRepository;
//import com.cookswp.milkstore.repository.ShoppingCartRepository;
import com.cookswp.milkstore.repository.product.ProductRepository;
import com.cookswp.milkstore.repository.shoppingCart.ShoppingCartRepository;
import com.cookswp.milkstore.repository.shoppingCartItem.ShoppingCartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService implements IShoppingCartService{

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;



    @Override
    public List<ShowCartModel> getCartByUserId(int userId) {
        Optional<ShoppingCart> cartOptional =shoppingCartRepository.findByUserId(userId);
        if (!cartOptional.isPresent()) {
            throw new RuntimeException("Shopping cart not found");
        }
        ShoppingCart shoppingCart = cartOptional.get();

        List<ShowCartModel.CartItemModel> items = shoppingCart.getItems().stream()
                .map(item -> {
                    ShowCartModel.CartItemModel cartItemModel = new ShowCartModel.CartItemModel();
                    cartItemModel.setProductId(item.getProduct().getProductID());
                    cartItemModel.setProductName(item.getProduct().getProductName());
                    cartItemModel.setQuantity(item.getQuantity());
                    cartItemModel.setPrice(item.getProduct().getPrice());
                    return cartItemModel;
                })
                .collect(Collectors.toList());
        ShowCartModel showCartModel = new ShowCartModel();
        showCartModel.setUserId(userId);
        showCartModel.setItems(items);

        return List.of(showCartModel);
    }

    @Override
    public ShoppingCart addToCart(AddToCart addToCart, int userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUserId(userId);
                    return shoppingCartRepository.save(newCart);
                });

        //sẽ sửa lại khi set được status sau khi thanh toán.
        Product product = productRepository.findById(addToCart.getProduct_id()).orElseThrow(() -> new RuntimeException("Product not found"));
        Optional<ShoppingCartItem> existingItemOpt = cart.getItems().stream().filter(item -> item.getProduct().getProductID() == product.getProductID()).findFirst();
        if (existingItemOpt.isPresent()) {
            ShoppingCartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + addToCart.getQuantity());
            shoppingCartItemRepository.save(existingItem);

        } else {
            ShoppingCartItem newItem = new ShoppingCartItem();
            newItem.setShoppingCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(addToCart.getQuantity());
            shoppingCartItemRepository.save(newItem);
        }
        return cart;
    }

    @Override
    public ShoppingCart deleteToCart(int cartId, int userId) {
        ShoppingCart cart = shoppingCartRepository.findByIdAndUserId(cartId, userId).orElseThrow(() -> new RuntimeException("Shopping cart not found"));
        shoppingCartRepository.delete(cart);
        return cart;
    }

    @Override
    public ShoppingCart updateItem(UpdateToCart updateToCart, int cartId, int userId) {
        ShoppingCart cart = shoppingCartRepository.findByIdAndUserId(cartId, userId).orElseThrow(() -> new RuntimeException("Shopping cart not found"));
        ShoppingCartItem item = cart.getItems().stream().filter(shoppingCartItem -> shoppingCartItem.getProduct().getProductID() == updateToCart.getProduct_id()).findFirst().orElseThrow(() -> new RuntimeException("Item not found in Cart !!"));


        //Sẽ sửa lại khi biết cách setup status
        item.setQuantity(updateToCart.getQuantity());
        shoppingCartItemRepository.save(item);
        return cart;
    }
}
