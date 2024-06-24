package com.cookswp.milkstore.service.vnpay;

import com.cookswp.milkstore.configuration.VNPayConfig;
import com.cookswp.milkstore.pojo.dtos.PaymentModel.PaymentDTO;
import com.cookswp.milkstore.pojo.entities.Payment;
import com.cookswp.milkstore.repository.payment.PaymentRepository;
import com.cookswp.milkstore.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VNPayServiceImpl implements VNPayService {

    private final VNPayConfig vnPayConfig;

    private final PaymentRepository paymentRepository;

    @Autowired
    public VNPayServiceImpl(VNPayConfig vnPayConfig, PaymentRepository paymentRepository) {
        this.vnPayConfig = vnPayConfig;
        this.paymentRepository = paymentRepository;
    }

    //Create purchase bill from product in cart to show
    public PaymentDTO.VNPayResponse createVNPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentDTO.VNPayResponse.builder().code("ok").message("success").paymentUrl(paymentUrl).build();
    }

    public Payment saveBillVNPayPayment(HttpServletRequest request) {
        Payment payment = new Payment();
        payment.setAmount(Long.valueOf(request.getParameter("vnp_Amount")));
        payment.setBankCode(request.getParameter("vnp_BankCode"));
        payment.setBankTranNo(request.getParameter("vnp_BankTranNo"));
        payment.setCardType(request.getParameter("vnp_CardType"));
        payment.setResponseCode(request.getParameter("vnp_ResponseCode"));
        payment.setOrderInfo(request.getParameter("vnp_OrderInfo"));
        payment.setTransactionNo(request.getParameter("vnp_TransactionNo"));
        payment.setTransactionStatus(request.getParameter("vnp_TransactionStatus"));
        return paymentRepository.save(payment);
    }
}
