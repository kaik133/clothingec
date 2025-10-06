package com.clothingec.controller;

import java.math.BigDecimal;

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
import com.clothingec.model.User;
import com.clothingec.service.CartService;
import com.clothingec.service.UserService;

@Controller
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String viewCart(Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());
        Cart cart = cartService.getOrCreateCart(user);
        BigDecimal total = cartService.calculateTotal(cart);
        
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart";
    }
    
    @PostMapping("/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity,
            Authentication authentication) {
        
        User user = userService.findByUsername(authentication.getName());
        cartService.addToCart(user, productId, quantity);
        return "redirect:/cart";
    }
    
    @PostMapping("/update/{itemId}")
    public String updateCartItem(
            @PathVariable Long itemId,
            @RequestParam Integer quantity,
            Authentication authentication) {
        
        User user = userService.findByUsername(authentication.getName());
        cartService.updateCartItemQuantity(user, itemId, quantity);
        return "redirect:/cart";
    }
    
    @PostMapping("/remove/{itemId}")
    public String removeFromCart(
            @PathVariable Long itemId,
            Authentication authentication) {
        
        User user = userService.findByUsername(authentication.getName());
        cartService.removeFromCart(user, itemId);
        return "redirect:/cart";
    }
    
    @PostMapping("/clear")
    public String clearCart(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        cartService.clearCart(user);
        return "redirect:/cart";
    }
}