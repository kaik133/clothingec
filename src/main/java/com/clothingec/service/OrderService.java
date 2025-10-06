package com.clothingec.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clothingec.model.Cart;
import com.clothingec.model.CartItem;
import com.clothingec.model.Order;
import com.clothingec.model.OrderItem;
import com.clothingec.model.User;
import com.clothingec.repository.OrderRepository;
import com.clothingec.repository.ProductRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Transactional
    public Order createOrder(User user, Cart cart, String shippingAddress, String phoneNumber) {
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setPhoneNumber(phoneNumber);
        order.setStatus(Order.Status.PENDING);
        
        BigDecimal total = BigDecimal.ZERO;
        
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setSubtotal(cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            
            order.addItem(orderItem);
            total = total.add(orderItem.getSubtotal());
        }
        
        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);
        
        // カートをクリア
        cartService.clearCart(user);
        
        return savedOrder;
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }
    
    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }
    
    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
    }
    
    @Transactional
    public Order updateOrderStatus(Long id, Order.Status status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        
        if (status == Order.Status.SHIPPED) {
            order.setShippedDate(LocalDateTime.now());
        } else if (status == Order.Status.DELIVERED) {
            order.setDeliveredDate(LocalDateTime.now());
        }
        
        return orderRepository.save(order);
    }
    
    public Long getTotalOrderCount() {
        return orderRepository.count();
    }
    
    public BigDecimal getTotalRevenue() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .filter(order -> order.getStatus() != Order.Status.CANCELLED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}