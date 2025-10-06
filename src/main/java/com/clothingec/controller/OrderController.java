package com.clothingec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clothingec.model.Cart;
import com.clothingec.model.Order;
import com.clothingec.model.User;
import com.clothingec.service.CartService;
import com.clothingec.service.OrderService;
import com.clothingec.service.UserService;

@Controller
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CartService cartService;
    
    @GetMapping
    public String listOrders(Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("orders", orderService.getUserOrders(user));
        return "order-list";
    }
    
    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order-detail";
    }
    
    @GetMapping("/checkout")
    public String checkout(Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());
        Cart cart = cartService.getOrCreateCart(user);
        
        if (cart.getItems().isEmpty()) {
            return "redirect:/cart";
        }
        
        model.addAttribute("cart", cart);
        model.addAttribute("total", cartService.calculateTotal(cart));
        model.addAttribute("user", user);
        return "checkout";
    }
    
    @PostMapping("/place")
    public String placeOrder(
            @RequestParam String shippingAddress,
            @RequestParam String phoneNumber,
            Authentication authentication) {
        
        User user = userService.findByUsername(authentication.getName());
        Cart cart = cartService.getOrCreateCart(user);
        
        Order order = orderService.createOrder(user, cart, shippingAddress, phoneNumber);
        
        return "redirect:/orders/" + order.getId() + "?success=true";
    }
}