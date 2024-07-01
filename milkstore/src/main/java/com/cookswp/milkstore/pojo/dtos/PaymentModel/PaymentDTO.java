package com.cookswp.milkstore.pojo.dtos.PaymentModel;

import lombok.Builder;

@Builder
public class PaymentDTO {
    public String code;
    public String message;
    public String paymentUrl;
}