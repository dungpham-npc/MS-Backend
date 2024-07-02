package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.PaymentModel.PaymentDTO;
import com.cookswp.milkstore.pojo.dtos.PaymentModel.RequestPayment;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.vnpay.VNPayServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class VNPayController {

    private final VNPayServiceImpl vnpayService;

    @Autowired
    public VNPayController(VNPayServiceImpl vnpayService) {
        this.vnpayService = vnpayService;
    }

    @GetMapping("/pay/{orderID}")
    public ResponseData<PaymentDTO> pay(@RequestBody RequestPayment requestPayment, @PathVariable String orderID) {
        return new ResponseData<>(HttpStatus.OK.value(), "Success", vnpayService.createVNPayPayment(requestPayment, orderID));
    }

    @GetMapping("/vnpay-callback")
    public ResponseData<PaymentDTO> callback(HttpServletRequest request) {
        String responseCode = request.getParameter("vnp_ResponseCode");
        if (responseCode.equals("00")) {
            return new ResponseData<>(HttpStatus.OK.value(), "Success", vnpayService.saveBillVNPayPayment(request));
        } else {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Failed", null);
        }
    }
}
