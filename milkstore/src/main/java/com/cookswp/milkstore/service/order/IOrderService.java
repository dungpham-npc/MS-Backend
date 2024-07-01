package com.cookswp.milkstore.service.order;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderDTO;
import com.cookswp.milkstore.pojo.entities.Order;

import java.util.List;


public interface IOrderService {

    List<Order> getAllOrders();
    Order getOrderById(long orderId);
    Order createOrder(OrderDTO orderDTO);
    Order updateOrder(long orderId, OrderDTO orderDTO);
    void deleteOrder(long orderId);
    OrderDTO updateOrderStatus(long orderId, Status status);

    List<Order>  getAll();

}
