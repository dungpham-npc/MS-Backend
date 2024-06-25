package com.cookswp.milkstore.service.vnpay;

import com.cookswp.milkstore.pojo.dtos.PaymentModel.PaymentDTO;
import com.cookswp.milkstore.pojo.entities.TransactionLog;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;

public interface VNPayService {

    public PaymentDTO.VNPayResponse createVNPayPayment(HttpServletRequest request);

    public TransactionLog saveBillVNPayPayment(HttpServletRequest request) throws ParseException;
}
