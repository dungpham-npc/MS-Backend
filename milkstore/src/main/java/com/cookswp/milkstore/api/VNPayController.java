package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.PaymentModel.PaymentDTO;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.vnpay.VNPayService;
import com.cookswp.milkstore.service.vnpay.VNPayServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/payment")
public class VNPayController {

    private final VNPayServiceImpl vnpayService;

    @Autowired
    public VNPayController(VNPayServiceImpl vnpayService) {
        this.vnpayService = vnpayService;
    }

    @GetMapping("/vn-pay")
    public ResponseData<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseData<>(HttpStatus.OK.value(), "Success", vnpayService.createVNPayPayment(request));
    }

    @GetMapping("/vn-pay-callback")
    public ResponseData<PaymentDTO.VNPayResponse> callback(HttpServletRequest request) throws ParseException {
        String responseCode = request.getParameter("vnp_ResponseCode");
        if (responseCode.equals("00")) {
            vnpayService.saveBillVNPayPayment(request);
            return new ResponseData<>(HttpStatus.OK.value(), "Success", vnpayService.responseVNPay(request));
        } else {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Failed", null);
        }

    }
}
