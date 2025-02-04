package com.example.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Order;
import com.example.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public Order getOrderById(UUID id) {
        return orderRepository.getOrderById(id);
    }
    public List<Order> getOrders() {
        return orderRepository.getOrders();
    }

    public void deleteOrderById(UUID id) throws IllegalArgumentException {
        orderRepository.deleteOrderById(id);
    }
    
    
}
