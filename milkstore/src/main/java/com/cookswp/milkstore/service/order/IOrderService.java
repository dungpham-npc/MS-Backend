package com.cookswp.milkstore.service.order;

import com.cookswp.milkstore.pojo.dtos.OrderModel.CreateOrderRequest;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderDTO;
import com.cookswp.milkstore.pojo.entities.Order;

import java.util.List;


public interface IOrderService {

    List<Order> getAllOrders();

    Order getOrderById(String id);

    Order createOrder(int userID, CreateOrderRequest orderDTO);

    Order updateOrder(String id, OrderDTO orderDTO);

    void deleteOrder(String id);

    Order updateOrderStatus(String id);

    List<Order> getAll();

}