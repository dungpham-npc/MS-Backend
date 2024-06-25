package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.PaymentModel.PaymentDTO;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.vnpay.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class VNPayController {

    private final VNPayService vnpayService;

    @PostMapping("/vn-pay")
    public ResponseData<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseData<>(HttpStatus.OK.value(), "Success", vnpayService.createVNPayPayment(request));
    }

    @GetMapping("/vn-pay-callback")
    public ResponseData<PaymentDTO.VNPayResponse> callback(HttpServletRequest request) throws ParseException {
        vnpayService.saveBillVNPayPayment(request);
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            PaymentDTO.VNPayResponse callbackResponse = PaymentDTO.VNPayResponse.builder()
                    .code("00")
                    .message("success call back")
                    .paymentUrl("")
                    .build();
            return new ResponseData<>(HttpStatus.OK.value(), "Success", callbackResponse);
        } else {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Failed", null);
        }
    }
}
