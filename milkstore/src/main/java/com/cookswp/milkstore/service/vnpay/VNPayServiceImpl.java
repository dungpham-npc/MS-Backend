package com.cookswp.milkstore.service.vnpay;

import com.cookswp.milkstore.configuration.VNPayConfig;
import com.cookswp.milkstore.pojo.dtos.PaymentModel.PaymentDTO;
import com.cookswp.milkstore.pojo.entities.Order;
import com.cookswp.milkstore.pojo.entities.TransactionLog;
import com.cookswp.milkstore.repository.transactionLog.TransactionLogRepository;
import com.cookswp.milkstore.service.order.IOrderService;
import com.cookswp.milkstore.service.order.OrderService;
import com.cookswp.milkstore.utils.VNPayUtil;

import java.text.SimpleDateFormat;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class VNPayServiceImpl implements VNPayService {

    private final VNPayConfig vnPayConfig;

    private final TransactionLogRepository transactionLogRepository;

    @Autowired
    public VNPayServiceImpl(VNPayConfig vnPayConfig, TransactionLogRepository transactionLogRepository) {
        this.vnPayConfig = vnPayConfig;
        this.transactionLogRepository = transactionLogRepository;
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
        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }

    @Override
    public PaymentDTO.VNPayResponse responseVNPay(HttpServletRequest request) {
        String responseCode = request.getParameter("vnp_ResponseCode");
        PaymentDTO.VNPayResponse callBack = null;
        switch (responseCode) {
            case "00": {
                callBack = PaymentDTO.VNPayResponse.builder()
                        .code("00")
                        .message("Giao dịch thành công")
                        .paymentUrl("")
                        .build();
                break;
            }
            case "09": {
                callBack = PaymentDTO.VNPayResponse.builder()
                        .code("09")
                        .message("Thẻ chưa được kích hoạt")
                        .paymentUrl("")
                        .build();
                break;
            }
            case "10": {
                callBack = PaymentDTO.VNPayResponse.builder()
                        .code("10")
                        .message("Nhập sai thông tin quá 3 lần")
                        .paymentUrl("")
                        .build();
                break;
            }
            case "11": {
                callBack = PaymentDTO.VNPayResponse.builder()
                        .code("11")
                        .message("Thẻ bị hết hạn")
                        .paymentUrl("")
                        .build();
                break;
            }
            case "12": {
                callBack = PaymentDTO.VNPayResponse.builder()
                        .code("12")
                        .message("Thẻ bị khóa")
                        .paymentUrl("")
                        .build();
                break;
            }
            case "51": {
                callBack = PaymentDTO.VNPayResponse.builder()
                        .code("51")
                        .message("Thẻ không đủ số dư")
                        .paymentUrl("")
                        .build();
                break;
            }
        }
        return callBack;
    }

    public void saveBillVNPayPayment(HttpServletRequest request) {
        TransactionLog trans = TransactionLog.builder()
                .order_id(1)
                .amount(Long.valueOf(request.getParameter("vnp_Amount")))
                .bankCode(request.getParameter("vnp_BankCode"))
                .bankTranNo(request.getParameter("vnp_BankTranNo"))
                .cardType(request.getParameter("vnp_CardType"))
                .orderInfo(request.getParameter("vnp_OrderInfo"))
                .responseCode(request.getParameter("vnp_ResponseCode"))
                .payDate(convertPayDate(request.getParameter("vnp_PayDate")))
                .transactionNo(request.getParameter("vnp_TransactionNo"))
                .transactionStatus(request.getParameter("vnp_TransactionStatus"))
                .build();
        transactionLogRepository.save(trans);
    }

    private String convertPayDate(String payDate) {
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = originalFormat.parse(payDate);

            DateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
            return targetFormat.format(date);
        } catch (Exception e) {
            System.out.println("Error at convertPayDate");
            return null;
        }
    }
}
