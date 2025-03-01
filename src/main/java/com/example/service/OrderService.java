package com.example.service;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class OrderService  extends MainService<Order> {
    @Autowired
    private OrderRepository orderRepository;

    public void addOrder(Order order){
        orderRepository.addOrder(order);
    }
    public ArrayList<Order> getOrders(){
        return orderRepository.getOrders();
    }
    public Order getOrderById(UUID orderId){

        Optional<Order> order = Optional.ofNullable(orderRepository.getOrderById(orderId));
        if (order.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found");
        }
        return orderRepository.getOrderById(orderId);
    }

    public void deleteOrderById(UUID orderId) throws IllegalArgumentException{

        Optional<Order> order = Optional.ofNullable(orderRepository.getOrderById(orderId));
        if (order.isEmpty()) {
            throw new IllegalArgumentException("order not found");
        }
        orderRepository.deleteOrderById(orderId);
    }
}

