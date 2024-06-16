package com.cookswp.milkstore.service.vnpay;

import com.cookswp.milkstore.pojo.dtos.PaymentModel.PaymentDTO;
import com.cookswp.milkstore.pojo.entities.Payment;
import jakarta.servlet.http.HttpServletRequest;

public interface VNPayService {

    public PaymentDTO.VNPayResponse createVNPayPayment(HttpServletRequest request);

    public Payment saveBillVNPayPayment(HttpServletRequest request);
}
