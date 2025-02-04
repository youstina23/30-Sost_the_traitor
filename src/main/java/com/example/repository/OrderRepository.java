package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.model.Order;

@Component
public class OrderRepository {

    public static List<Order> orders = new ArrayList<>();

    public OrderRepository() {
        
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
    public List<Order> getOrders() {
        return orders;
    }
    public Order getOrderById(UUID id) {
        for (Order order : orders) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }
    public void deleteOrderById(UUID id) throws IllegalArgumentException {
        for (Order order : orders) {
            if (order.getId().equals(id)) {
                orders.remove(order);
                return;
            }
        }
        throw new IllegalArgumentException("Order not found");
    }
    
    
}
