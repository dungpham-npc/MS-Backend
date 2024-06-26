package com.cookswp.milkstore.service.vnpay;

import com.cookswp.milkstore.pojo.dtos.PaymentModel.PaymentDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;

public interface VNPayService {

    public PaymentDTO.VNPayResponse createVNPayPayment(HttpServletRequest request);

    public void saveBillVNPayPayment(HttpServletRequest request) throws ParseException;

    public String checkResponseCode(HttpServletRequest request);
}
