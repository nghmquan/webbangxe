package com.vanlang.webbanxe.controller;

import com.vanlang.webbanxe.model.CartItem;
import com.vanlang.webbanxe.service.CartService;
import com.vanlang.webbanxe.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        // Bạn có thể thêm logic để chuẩn bị dữ liệu cho trang checkout nếu cần
        return "cart/checkout";
    }

    @PostMapping("/submit")
    public String submitOrder(String customerName, String shippingAddress, String phoneNumber, String email, String notes, String paymentMethod) {
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return "redirect:/cart"; // Redirect if cart is empty
        }
        orderService.createOrder(customerName, shippingAddress, phoneNumber, email, notes, paymentMethod, cartItems);
        cartService.clearCard(); // Clear the cart after order is submitted
        return "redirect:/order/confirmation";
    }

    @GetMapping("/confirmation")
    public String orderConfirmation(Model model) {
        model.addAttribute("message", "Your order has been successfully placed.");
        return "cart/order-confirmation";
    }
}
