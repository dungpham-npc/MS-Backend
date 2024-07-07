package com.cookswp.milkstore.service.order;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.OrderModel.CreateOrderRequest;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderDTO;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderItemDTO;
import com.cookswp.milkstore.pojo.entities.*;
import com.cookswp.milkstore.repository.order.OrderRepository;
import com.cookswp.milkstore.repository.orderItem.OrderItemRepository;
import com.cookswp.milkstore.repository.shoppingCart.ShoppingCartRepository;
import com.cookswp.milkstore.repository.shoppingCartItem.ShoppingCartItemRepository;
import com.cookswp.milkstore.repository.transactionLog.TransactionLogRepository;
import com.cookswp.milkstore.repository.user.UserRepository;
import com.cookswp.milkstore.service.firebase.FirebaseService;
import com.cookswp.milkstore.service.product.ProductService;
import com.cookswp.milkstore.service.shoppingcart.ShoppingCartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    private final ShoppingCartItemRepository shoppingCartItemRepository;

    private final TransactionLogRepository transactionLogRepository;

    private final UserRepository userRepository;

    private final OrderItemRepository orderItemRepository;


    private final FirebaseService firebaseService;
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService, ShoppingCartItemRepository shoppingCartItemRepository, TransactionLogRepository transactionLogRepository, UserRepository userRepository, OrderItemRepository orderItemRepository, FirebaseService firebaseService, ShoppingCartService shoppingCartService, ShoppingCartRepository shoppingCartRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.transactionLogRepository = transactionLogRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.firebaseService = firebaseService;
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartRepository = shoppingCartRepository;
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
        //order.setCarID(orderDTO.getCartID());
        order.setReceiverName(orderDTO.getReceiverName());
        order.setReceiverPhone(orderDTO.getReceiverPhoneNumber());
        order.setOrderStatus(Status.IN_CART);
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(orderDTO.getShippingAddress());
        // order.setCart(orderDTO.);

        //Save Cart Information before clear Cart
        if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
            saveOrderItems(order, orderDTO.getItems());
        }


        return orderRepository.save(order);
    }

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

    @Transactional
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
            reduceProductQuantity(order.getId());
            Optional<ShoppingCart> cartOptional = shoppingCartRepository.findByUserId(order.getUserId());
            if(cartOptional.isPresent()) {
                ShoppingCart shoppingCart = cartOptional.get();
                shoppingCart.getItems().clear();
                shoppingCartRepository.save(shoppingCart);
            }
        }
        return orderRepository.save(order);
    }


    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    private void reduceProductQuantity(String orderId) {
        List<ShoppingCartItem> cartItems = shoppingCartItemRepository.findById(String.valueOf(orderId));

        for (ShoppingCartItem item : cartItems) {
            productService.reduceQuantityProduct(item.getProduct().getProductID(), item.getQuantity());
        }
    }

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    //Method to Confirm Order
    @Transactional
    public Order confirmOrderToShipping(String OrderId) {
        Order order = getOrderById(OrderId);
        if (order.getOrderStatus() == Status.PAID) {
            order.setOrderStatus(Status.IN_DELIVERY);
            return orderRepository.save(order);
        } else {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }
    }

    //Method to Cancel Order with Reason
    @Transactional
    public Order cancelOrder(String OrderId, String reason) {
        Order order = getOrderById(OrderId);
        order.setOrderStatus(Status.CANNOT_DELIVER);
        order.setFailureReasonNote(reason);
        return orderRepository.save(order);
    }

    //Method to Transfer InDelivery to CompleteTransaction Status
    @Transactional
    public Order completeOrderTransaction(String OrderId, MultipartFile EvidenceImage) {
        Order order = getOrderById(OrderId);
        String imageURL = firebaseService.upload(EvidenceImage);
        if (order.getOrderStatus() == Status.IN_DELIVERY && EvidenceImage != null) {
            order.setOrderStatus(Status.COMPLETE_EXCHANGE);
            order.setImage(imageURL);
        } else {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }
        return orderRepository.save(order);
    }

    public Order cannotOrderInDelivery(String OrderId) {
        Order order = getOrderById(OrderId);
        if (order.getOrderStatus() == Status.CANNOT_DELIVER) {
            order.setOrderStatus(Status.IN_DELIVERY);
            return orderRepository.save(order);
        } else {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }
    }

    //Method to get all order from an User
    public List<Order> getOrderByAnUserId(int userId) {
        return orderRepository.findByUserId(userId);
    }


    // Phương thức riêng để lưu thông tin sản phẩm từ giỏ hàng vào OrderItem
    private void saveOrderItems(Order order, List<OrderItemDTO> items) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO item : items) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setProductName(item.getProductName());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());
            orderItems.add(orderItem);
        }
        orderItemRepository.saveAll(orderItems);
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
