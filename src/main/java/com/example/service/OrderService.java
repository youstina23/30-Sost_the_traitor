package com.example.service;

import com.example.model.Order;
import com.example.model.User;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    UserService userService;
    @Autowired
    private User user;

    public OrderService(OrderRepository orderRepository, @Lazy UserService userService) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public void addOrder(Order order){
        add(order);
    }

    public ArrayList<Order> getOrders(){
        return getAll();
    }

    public Order getOrderById(UUID orderId){
        Optional<Order> order = Optional.ofNullable(orderRepository.getOrderById(orderId));
        if (order.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        return getById(orderId);
    }

    public void deleteOrderById(UUID orderId) throws IllegalArgumentException{
        Order order = orderRepository.getOrderById(orderId);

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        userService.removeOrderFromUser(order.getUserId(), orderId);
        orderRepository.deleteOrderById(orderId);
//        delete(orderId);
    }
}

