package com.cookswp.milkstore.service.order;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.OrderModel.CreateOrderRequest;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderDTO;
import com.cookswp.milkstore.pojo.entities.*;
import com.cookswp.milkstore.repository.order.OrderRepository;
import com.cookswp.milkstore.repository.shoppingCartItem.ShoppingCartItemRepository;
import com.cookswp.milkstore.repository.transactionLog.TransactionLogRepository;
import com.cookswp.milkstore.repository.user.UserRepository;
import com.cookswp.milkstore.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    private final ShoppingCartItemRepository shoppingCartItemRepository;

    private final TransactionLogRepository transactionLogRepository;

    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService, ShoppingCartItemRepository shoppingCartItemRepository, TransactionLogRepository transactionLogRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.transactionLogRepository = transactionLogRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    //Tạo order dựa trên userID của người dùng
    @Override
    @Transactional
    public Order createOrder(int userID, CreateOrderRequest orderDTO) {
        User user = userRepository.findByUserId(userID);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Order order = new Order();
        UUID id = UUID.randomUUID();
        order.setId(id.toString());
        order.setUserId(userID);
        order.setReceiverName(orderDTO.getReceiverName());
        order.setReceiverPhone(orderDTO.getReceiverPhoneNumber());
        order.setOrderStatus(Status.IN_CART);
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(orderDTO.getShippingAddress());
        return orderRepository.save(order);
    }

    //Status status = orderDTO.getStatus();
    //        if (status == Status.PAID) {
    //            Order order = new Order();
    //            order.setUserId(orderDTO.getUserId());
    //            order.setOrderStatus(orderDTO.getStatus());
    //            order.setTotalPrice(orderDTO.getTotalPrice());
    //            order.setOrderDate(orderDTO.getOrderDate());
    //            order.setShippingAddress(orderDTO.getShippingAddress());
    //            return orderRepository.save(order);
    //        } else if (orderDTO.getStatus() == Status.IN_CHECKOUT) {
    //            throw new RuntimeException(PaymentStatus.PendingPayment + "In payment time");
    //        } else {
    //            throw new RuntimeException(PaymentStatus.Failed + "Payment not complete");
    //        }


    @Override
    @Transactional
    public Order updateOrder(String orderId, OrderDTO orderDTO) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        findOrder.setUserId(orderDTO.getUserId());
        findOrder.setOrderDate(orderDTO.getOrderDate());

        findOrder.setTotalPrice(orderDTO.getTotalPrice());
        findOrder.setShippingAddress(orderDTO.getShippingAddress());
        return orderRepository.save(findOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order updateOrderStatus(String orderID) {
        Optional<Order> findOrder = orderRepository.findById(orderID);
        if (findOrder.isEmpty()) {
            return null;
        }
        Order order = findOrder.get();
        String statusCode = transactionLogRepository.findTransactionNoByTxnRef(orderID);
        if ("00".equals(statusCode)) {
            order.setOrderStatus(Status.PAID);
        }

        orderRepository.save(order);
        return order;
    }


    //Order order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    //        order.setOrderStatus(status);
    //        order = orderRepository.save(order);
    //
    //        // Reduce product quantity when order status is set
    //        if (status == Status.PAID) {
    //            reduceProductQuantity(order.getUserId());
    //        }
    //
    //        // Check Delivery Status
    //        if (status == Status.IN_DELIVERY) {
    //
    //        } else {
    //            throw new RuntimeException(Status.CANNOT_DELIVER + "Reason");
    //        }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }


    // Reduce product quantity for a given order
    private void reduceProductQuantity(long orderId) {
        List<ShoppingCartItem> cartItems = shoppingCartItemRepository.findById(orderId);

        for (ShoppingCartItem item : cartItems) {
            productService.reduceQuantityProduct(item.getProduct().getProductID(), item.getQuantity());
        }
    }

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    //Method to Confirm Order
    @Transactional
    public void confirmOrderToShipping(String OrderId) {
        Order order = getOrderById(OrderId);
        if (order.getOrderStatus() == Status.PAID) {
            order.setOrderStatus(Status.IN_DELIVERY);
            orderRepository.save(order);
        } else {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }
    }

    //Method to Cancel Order with Reason
    public void cancelOrder(String OrderId, String reason) {
        Order order = getOrderById(OrderId);
        order.setOrderStatus(Status.CANNOT_DELIVER);
        order.setFailureReasonNote(reason);
        orderRepository.save(order);
    }

    //Method to get all order from an User
    public List<Order> getOrderByAnUserId(int userId) {
        return orderRepository.findByUserId(userId);
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
}
