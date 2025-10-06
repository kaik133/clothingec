package com.clothingec.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.clothingec.model.Order;
import com.clothingec.model.Product;
import com.clothingec.service.OrderService;
import com.clothingec.service.ProductService;
import com.clothingec.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private OrderService orderService;
    
    private static final String UPLOAD_DIR = "src/main/resources/static/images/products/";
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("productCount", productService.getAllProducts().size());
        model.addAttribute("userCount", userService.getAllUsers().size());
        model.addAttribute("orderCount", orderService.getTotalOrderCount());
        model.addAttribute("totalRevenue", orderService.getTotalRevenue());
        return "admin/dashboard";
    }
    
    // Product Management
    @GetMapping("/products")
    public String manageProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/product-list";
    }
    
    @GetMapping("/products/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product-register";
    }
    
    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product, 
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        
        // 画像ファイルのアップロード処理
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imageUrl = saveImage(imageFile);
                product.setImageUrl(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
                // エラーハンドリング
            }
        }
        
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }
    
    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        model.addAttribute("product", product);
        return "admin/product-edit";
    }
    
    @PostMapping("/products/update/{id}")
    public String updateProduct(@PathVariable Long id, 
                               @ModelAttribute Product product,
                               @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        
        // 画像ファイルのアップロード処理
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imageUrl = saveImage(imageFile);
                product.setImageUrl(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        productService.updateProduct(id, product);
        return "redirect:/admin/products";
    }
    
    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
    
    @PostMapping("/products/status/{id}")
    public String updateProductStatus(
            @PathVariable Long id,
            @RequestParam Product.Status status) {
        productService.updateProductStatus(id, status);
        return "redirect:/admin/products";
    }
    
    // User Management
    @GetMapping("/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/user-management";
    }
    
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
    
    // Order Management
    @GetMapping("/orders")
    public String manageOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin/order-management";
    }
    
    @GetMapping("/orders/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "admin/order-detail";
    }
    
    @PostMapping("/orders/status/{id}")
    public String updateOrderStatus(
            @PathVariable Long id,
            @RequestParam Order.Status status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/admin/orders";
    }
    
    // 画像保存用のヘルパーメソッド
    private String saveImage(MultipartFile file) throws IOException {
        // ディレクトリが存在しない場合は作成
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // ファイル名を生成（重複を避けるためUUIDを使用）
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;
        
        // ファイルを保存
        Path filePath = uploadPath.resolve(newFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        // 相対パスを返す（/images/products/ファイル名）
        return "/images/products/" + newFilename;
    }
}