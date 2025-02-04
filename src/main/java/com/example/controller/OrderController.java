package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import com.example.model.Order;
import com.example.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public void addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable UUID id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrderById(@PathVariable UUID id) {
        try{

            orderService.deleteOrderById(id);
            return "Order deleted successfully";
        }catch(IllegalArgumentException e){
            return "Order not found";
        }
    }


    
    
}
