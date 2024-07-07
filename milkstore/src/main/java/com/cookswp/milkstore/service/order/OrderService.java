package com.cookswp.milkstore.service.order;

import com.cookswp.milkstore.enums.PaymentStatus;
import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderDTO;
import com.cookswp.milkstore.pojo.dtos.PaymentModel.PaymentDTO;
import com.cookswp.milkstore.pojo.entities.*;
import com.cookswp.milkstore.repository.order.OrderRepository;
import com.cookswp.milkstore.repository.shoppingCart.ShoppingCartRepository;
import com.cookswp.milkstore.repository.shoppingCartItem.ShoppingCartItemRepository;
import com.cookswp.milkstore.repository.transactionLog.TransactionLogRepository;
import com.cookswp.milkstore.service.product.ProductService;
import com.cookswp.milkstore.service.shoppingcart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    private final ShoppingCartService shoppingCartService;

    private final ProductService productService;

    private final ShoppingCartItemRepository shoppingCartItemRepository;

    private final TransactionLogRepository transactionLogRepository;

//    private final ShoppingCartRepository shoppingCartRepository;

//    private final ShoppingCart cart;

    private Order order = null;

    @Autowired
    public OrderService(OrderRepository orderRepository, ShoppingCartService shoppingCartService, ProductService productService, ShoppingCartItemRepository shoppingCartItemRepository, TransactionLogRepository transactionLogRepository) {
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.transactionLogRepository = transactionLogRepository;
    }


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        Status status = orderDTO.getStatus();
        if (status == Status.PAID) {
            Order order = new Order();
            order.setUserId(orderDTO.getUserId());
            order.setOrderStatus(orderDTO.getStatus());
            order.setTotalPrice(orderDTO.getTotalPrice());
            order.setOrderDate(orderDTO.getOrderDate());
            order.setShippingAddress(orderDTO.getShippingAddress());
            return orderRepository.save(order);
        } else if (status == Status.IN_CHECKOUT) {
            throw new RuntimeException(PaymentStatus.PendingPayment + "In payment time");
        } else {
            throw new RuntimeException(PaymentStatus.Failed + "Payment not complete");
        }
    }


    @Override
    @Transactional
    public Order updateOrder(long orderId, OrderDTO orderDTO) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        findOrder.setUserId(orderDTO.getUserId());
        findOrder.setOrderDate(orderDTO.getOrderDate());

        findOrder.setTotalPrice(orderDTO.getTotalPrice());
        findOrder.setShippingAddress(orderDTO.getShippingAddress());
        return orderRepository.save(findOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    @Transactional
    public OrderDTO updateOrderStatus(long orderId, Status status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setOrderStatus(status);
        order = orderRepository.save(order);

        // Reduce product quantity when order status is set
        if (status == Status.PAID) {
            reduceProductQuantity(order.getId());
        }

        // Check Delivery Status
        if (status == Status.IN_DELIVERY) {

        } else {
            throw new RuntimeException(Status.CANNOT_DELIVER + "Reason");
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
    public Order getOrderById(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
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
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Long getNumberOfOrdersByStatus(String status) throws IllegalArgumentException{
        return orderRepository.getNumberOfOrdersByStatus(Status.valueOf(status));
    }

    @Override
    public Long getTotalOrders() {
        return orderRepository.getTotalOrders();
    }

    @Override
    public Double getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }

    @Override
    public Map<Status, Long> getOrderStatusBreakdown() {
        List<Object[]> result = orderRepository.getOrderStatusBreakdown();
        return result.stream().collect(Collectors.toMap(
                row -> (Status) row[0],
                row -> (Long) row[1]
        ));
    }

    @Override
    public Double getAverageRevenuePerOrder() {
        return orderRepository.getAverageRevenuePerOrder();
    }

    @Override
    public Long getOrdersByMonth(int startMonth, int endMonth) {
        return orderRepository.getOrdersByMonth(startMonth, endMonth);
    }

    @Override
    public Map<Integer, Long> getOrderCountsForYear(int year) {
        List<Integer> months = IntStream.rangeClosed(1, 12).boxed().toList();
        List<Object[]> results = orderRepository.getOrderCountsByMonth(year);

        Map<Integer, Long> ordersByMonth = new HashMap<>();
        for (Integer month : months) {
            ordersByMonth.put(month, 0L); // Initialize all months with 0
        }

        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            Long count = (Long) result[1];
            ordersByMonth.put(month, count);
        }

        return ordersByMonth;
    }

    //Bao beu bi gay
}
