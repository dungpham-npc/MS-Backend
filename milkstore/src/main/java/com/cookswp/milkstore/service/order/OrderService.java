package com.cookswp.milkstore.service.order;

import com.cookswp.milkstore.enums.PaymentStatus;
import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderDTO;
import com.cookswp.milkstore.pojo.dtos.PaymentModel.PaymentDTO;
import com.cookswp.milkstore.pojo.entities.Order;
import com.cookswp.milkstore.pojo.entities.Payment;
import com.cookswp.milkstore.pojo.entities.ShoppingCart;
import com.cookswp.milkstore.pojo.entities.ShoppingCartItem;
import com.cookswp.milkstore.repository.order.OrderRepository;
import com.cookswp.milkstore.repository.shoppingCart.ShoppingCartRepository;
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

    private final OrderRepository orderRepository;

    private final ShoppingCartService shoppingCartService;

    private final ProductService productService;

    private final ShoppingCartItemRepository shoppingCartItemRepository;

//    private final ShoppingCartRepository shoppingCartRepository;

//    private final ShoppingCart cart;

    private Order order = null;

    public OrderService(OrderRepository orderRepository, ShoppingCartService shoppingCartService, ProductService productService, ShoppingCartItemRepository shoppingCartItemRepository) {
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
//        this.shoppingCartRepository = shoppingCartRepository;
//        this.cart = cart;
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
        Order findOrder = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found"));
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
    public Order getOrderById(long orderId) {
        return  orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
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
