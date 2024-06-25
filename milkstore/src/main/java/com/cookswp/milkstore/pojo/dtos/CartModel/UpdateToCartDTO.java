package com.cookswp.milkstore.pojo.dtos.CartModel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateToCartDTO {

    private int product_id;
    private int quantity;


}
