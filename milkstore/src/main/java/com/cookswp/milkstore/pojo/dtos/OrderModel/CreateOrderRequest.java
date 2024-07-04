package com.cookswp.milkstore.pojo.dtos.OrderModel;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateOrderRequest {

    private String receiverName;
    private String receiverPhoneNumber;
    private BigDecimal totalPrice;
    private String shippingAddress;

}
