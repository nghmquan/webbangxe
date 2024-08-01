package com.vanlang.webbanxe.controller;

import com.vanlang.webbanxe.model.Order;
import com.vanlang.webbanxe.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/orders/list";
    }

    @GetMapping("/{orderId}")
    public String viewOrder(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId); // Tạo phương thức getOrderById trong OrderService
        model.addAttribute("order", order);
        return "admin/orders/view";
    }

    // Bạn có thể thêm các phương thức khác để xử lý đơn hàng như xác nhận đơn hàng, hủy đơn hàng, v.v.
}
