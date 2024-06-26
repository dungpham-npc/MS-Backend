package com.cookswp.milkstore.pojo.dtos.CartModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

;
import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowCartModelDTO {
    private int userId;
    private List<CartItemModel> items;



    @Getter
    @Setter
    public static class CartItemModel {
        private int productId;
        private String productName;
        private int quantity;
        private BigDecimal price;




    }
}
