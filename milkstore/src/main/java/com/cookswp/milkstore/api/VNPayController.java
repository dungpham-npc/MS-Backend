package com.cookswp.milkstore.api;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderDTO;
import com.cookswp.milkstore.pojo.dtos.PaymentModel.PaymentDTO;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.order.OrderService;
import com.cookswp.milkstore.service.vnpay.VNPayService;
import com.cookswp.milkstore.service.vnpay.VNPayServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/payment")
public class VNPayController {

    private final VNPayServiceImpl vnpayService;

    private final OrderService orderService;

    @Autowired
    public VNPayController(VNPayServiceImpl vnpayService, OrderService orderService) {
        this.vnpayService = vnpayService;
        this.orderService = orderService;
    }

    @GetMapping("/vnpay")
    public ResponseData<PaymentDTO> pay(HttpServletRequest request) {
        return new ResponseData<>(HttpStatus.OK.value(), "Success", vnpayService.createVNPayPayment(request));
    }

    @GetMapping("/vnpay-callback")
    public ResponseData<PaymentDTO> callback(HttpServletRequest request) {
        String responseCode = request.getParameter("vnp_ResponseCode");
        if (responseCode.equals("00")) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setStatus(Status.PAID);
            orderService.createOrder(orderDTO);
            vnpayService.saveBillVNPayPayment(request);
            return new ResponseData<>(HttpStatus.OK.value(), "Success", vnpayService.responseVNPay(request));
        } else {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Failed", null);
        }
    }
}
