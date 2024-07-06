package com.cookswp.milkstore.pojo.dtos.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemDTO {
    private int productId;
    private String productName;
    private int quantity;
    private BigDecimal price;

}
