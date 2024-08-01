package com.vanlang.webbanxe.service;

import com.vanlang.webbanxe.model.CartItem;
import com.vanlang.webbanxe.model.Order;
import com.vanlang.webbanxe.model.OrderDetail;
import com.vanlang.webbanxe.repository.OrderDetailRepository;
import com.vanlang.webbanxe.repository.OrderRepository;
import com.vanlang.webbanxe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void createOrder(String customerName, String shippingAddress, String phoneNumber, String email, String notes, String paymentMethod, List<CartItem> cartItems) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setShippingAddress(shippingAddress);
        order.setPhoneNumber(phoneNumber);
        order.setEmail(email);
        order.setNotes(notes);
        order.setPaymentMethod(paymentMethod);

        orderRepository.save(order);

        for (CartItem item : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(item.getProduct());
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setOrder(order);

            orderDetailRepository.save(orderDetail);
        }
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
    }
}
