package com.cookswp.milkstore.service.order;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderDTO;
import com.cookswp.milkstore.pojo.entities.Order;
import com.cookswp.milkstore.repository.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService  implements IOrderService{
    @Autowired
    private OrderRepository orderRepository;


    @Override
    public List<Object> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toOrderDTO).collect(Collectors.toList());
    }

    //Change Order Entity to OrderDTO
    private Object toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(orderDTO.getUserId());
        orderDTO.setStatus(order.getOrderStatus());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setShippingAddress(order.getShippingAddress());
        return orderDTO;

    }

    //Change OrderDTO to Order entity
    private Order toOrderEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setUserId(order.getUserId());
        order.setOrderStatus(orderDTO.getStatus());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setShippingAddress(orderDTO.getShippingAddress());
        return order;


    }

    @Override
    public OrderDTO getOrderById(int orderId) {
        Order order = orderRepository.findById((long) orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return (OrderDTO) toOrderDTO(order);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = toOrderEntity(orderDTO);
        order.setOrderStatus(Status.IN_CART); //Trien khai status In_Cart o day de xac dinh trang thai van con la product list
        order = orderRepository.save(order);
        return (OrderDTO) toOrderDTO(order);
    }

    @Override
    public OrderDTO updateOrder(int orderId, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(int orderId) {

    }

    @Override
    public OrderDTO updateOrderStatus(long orderId, Status status) {
        return null;
    }
}
