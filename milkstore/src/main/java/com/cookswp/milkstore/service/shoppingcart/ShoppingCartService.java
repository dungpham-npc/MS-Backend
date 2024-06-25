package com.cookswp.milkstore.service.shoppingcart;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.pojo.dtos.CartModel.AddToCartDTO;
import com.cookswp.milkstore.pojo.dtos.CartModel.ShowCartModelDTO;
import com.cookswp.milkstore.pojo.dtos.CartModel.UpdateToCartDTO;
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
    public List<ShowCartModelDTO> getCartByUserId(int userId) {
        Optional<ShoppingCart> cartOptional =shoppingCartRepository.findByUserId(userId);
        if (!cartOptional.isPresent()) {
            throw new RuntimeException("Shopping cart not found");
        }
        ShoppingCart shoppingCart = cartOptional.get();

        List<ShowCartModelDTO.CartItemModel> items = shoppingCart.getItems().stream()
                .map(item -> {
                    ShowCartModelDTO.CartItemModel cartItemModel = new ShowCartModelDTO.CartItemModel();
                    cartItemModel.setProductId(item.getProduct().getProductID());
                    cartItemModel.setProductName(item.getProduct().getProductName());
                    cartItemModel.setQuantity(item.getQuantity());
                    cartItemModel.setPrice(item.getProduct().getPrice());
                    return cartItemModel;
                })
                .collect(Collectors.toList());
        ShowCartModelDTO showCartModelDTO = new ShowCartModelDTO();
        showCartModelDTO.setUserId(userId);
        showCartModelDTO.setItems(items);

        return List.of(showCartModelDTO);
    }

    @Override
    public ShoppingCart addToCart(AddToCartDTO addToCartDTO, int userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUserId(userId);
                    return shoppingCartRepository.save(newCart);
                });

        //sẽ sửa lại khi set được status sau khi thanh toán.
        Product product = productRepository.findById(addToCartDTO.getProduct_id()).orElseThrow(() -> new RuntimeException("Product not found"));
        Optional<ShoppingCartItem> existingItemOpt = cart.getItems().stream().filter(item -> item.getProduct().getProductID() == product.getProductID()).findFirst();
        if (existingItemOpt.isPresent()) {
            ShoppingCartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + addToCartDTO.getQuantity());
            shoppingCartItemRepository.save(existingItem);

        } else {
            ShoppingCartItem newItem = new ShoppingCartItem();
            newItem.setShoppingCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(addToCartDTO.getQuantity());
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
    public ShoppingCart updateItem(UpdateToCartDTO updateToCartDTO, int cartId, int userId) {
        ShoppingCart cart = shoppingCartRepository.findByIdAndUserId(cartId, userId).orElseThrow(() -> new RuntimeException("Shopping cart not found"));
        ShoppingCartItem item = cart.getItems().stream().filter(shoppingCartItem -> shoppingCartItem.getProduct().getProductID() == updateToCartDTO.getProduct_id()).findFirst().orElseThrow(() -> new RuntimeException("Item not found in Cart !!"));


        //Sẽ sửa lại khi biết cách setup status
        item.setQuantity(updateToCartDTO.getQuantity());
        shoppingCartItemRepository.save(item);
        return cart;
    }

    public void updateCartStatus (ShoppingCart cart, Status status) {
        cart.setStatus(status);
        shoppingCartRepository.save(cart);
    }
}
