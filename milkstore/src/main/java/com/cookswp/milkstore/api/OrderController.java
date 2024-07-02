package com.cookswp.milkstore.api;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.pojo.dtos.OrderModel.CreateOrderRequest;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderDTO;
import com.cookswp.milkstore.pojo.entities.Order;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseData<List<Order>> getAllOrders() {
        return new ResponseData<>(HttpStatus.OK.value(), "LIST ORDER", orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseData<Order> getOrderById(@PathVariable String id) {
        return new ResponseData<>(HttpStatus.OK.value(), "GET ORDER BY ID", orderService.getOrderById(id));
    }

    @PostMapping("/{userID}")
    public ResponseData<Order> createOrder(@PathVariable int userID, @RequestBody CreateOrderRequest orderDTO) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "CREATE ORDER", orderService.createOrder(userID, orderDTO));
    }


    @PutMapping("/{id}")
    public ResponseData<Order> updateOrder(@PathVariable String id, @RequestBody OrderDTO orderDTO) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "UPDATE ORDER", orderService.updateOrder(id, orderDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "DELETE ORDER", null);
    }

    @PutMapping("/{orderID}")
    public ResponseData<Order> updateOrderStatus(@PathVariable String orderID) {
        return new ResponseData<>(HttpStatus.OK.value(), "Update successfully", orderService.updateOrderStatus(orderID));
    }
}