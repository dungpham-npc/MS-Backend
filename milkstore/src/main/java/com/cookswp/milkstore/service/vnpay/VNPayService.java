package com.cookswp.milkstore.service.vnpay;

import com.cookswp.milkstore.pojo.dtos.PaymentModel.PaymentDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;

public interface VNPayService {

    public PaymentDTO.VNPayResponse createVNPayPayment(HttpServletRequest request);

    public void saveBillVNPayPayment(HttpServletRequest request) throws ParseException;

    public PaymentDTO.VNPayResponse responseVNPay(HttpServletRequest request);
}
