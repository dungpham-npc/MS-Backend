package com.cookswp.milkstore.service.order;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderDTO;

import java.util.List;


public interface IOrderService {

    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(int orderId);
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(int orderId, OrderDTO orderDTO);
    void deleteOrder(int orderId);
    OrderDTO updateOrderStatus(long orderId, Status status);


}
