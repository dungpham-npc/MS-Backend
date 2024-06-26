package com.cookswp.milkstore.api;

import com.cookswp.milkstore.enums.Status;
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
    public ResponseData<Order> getOrderById(@PathVariable long id) {
       return new ResponseData<>(HttpStatus.OK.value(), "GET ORDER BY ID", orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseData<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "CREATE ORDER", orderService.createOrder(orderDTO));
    }

    @PutMapping("/{id}")
    public ResponseData<Order> updateOrder(@PathVariable long id, @RequestBody OrderDTO orderDTO) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "UPDATE ORDER", orderService.updateOrder(id, orderDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "DELETE ORDER", null);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable long id, @RequestBody Status status) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }
}
