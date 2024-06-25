package com.cookswp.milkstore.service.order;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderDTO;
import com.cookswp.milkstore.pojo.entities.Order;
import com.cookswp.milkstore.pojo.entities.ShoppingCartItem;
import com.cookswp.milkstore.repository.order.OrderRepository;
import com.cookswp.milkstore.repository.shoppingCartItem.ShoppingCartItemRepository;
import com.cookswp.milkstore.service.product.ProductService;
import com.cookswp.milkstore.service.shoppingcart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toOrderDTO).collect(Collectors.toList());
    }

    // Convert Order Entity to OrderDTO
    private OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(order.getUserId());
        orderDTO.setStatus(order.getOrderStatus());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setShippingAddress(order.getShippingAddress());
        return orderDTO;
    }

    // Convert OrderDTO to Order entity
    private Order toOrderEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setUserId(orderDTO.getUserId());
        order.setOrderStatus(orderDTO.getStatus());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setShippingAddress(orderDTO.getShippingAddress());
        return order;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = toOrderEntity(orderDTO);
        order.setOrderStatus(Status.IN_CART); // Set status to indicate product list
        order = orderRepository.save(order);
        return toOrderDTO(order);
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(int orderId, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById((long) orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        existingOrder.setUserId(existingOrder.getUserId());
        existingOrder.setOrderStatus(orderDTO.getStatus());
        existingOrder.setTotalPrice(orderDTO.getTotalPrice());
        existingOrder.setShippingAddress(orderDTO.getShippingAddress());
        existingOrder = orderRepository.save(existingOrder);
        return toOrderDTO(existingOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(int orderId) {
        orderRepository.deleteById((long) orderId);
    }

    @Override
    @Transactional
    public OrderDTO updateOrderStatus(long orderId, Status status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(status);
        order = orderRepository.save(order);

        // Reduce product quantity when order status is set
        if (status == Status.PAID) {
            reduceProductQuantity(order.getId());
        }

        return toOrderDTO(order);
    }

    // Reduce product quantity for a given order
    private void reduceProductQuantity(long orderId) {
        List<ShoppingCartItem> cartItems = shoppingCartItemRepository.findById(orderId);

        for (ShoppingCartItem item : cartItems) {
            productService.reduceQuantityProduct(item.getProduct().getProductID(), item.getQuantity());
        }
    }

    @Override
    public OrderDTO getOrderById(int orderId) {
        Order order = orderRepository.findById((long) orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return toOrderDTO(order);
    }
}
