package com.example.controller;

import com.example.model.Order;
import com.example.service.OrderService;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @PostMapping("/")
    public void addOrder(@RequestBody Order order){
        orderService.addOrder(order);
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable UUID orderId){
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/")
    public ArrayList<Order> getOrders(){
        return orderService.getOrders();
    }

    @DeleteMapping("/delete/{orderId}")
    public String deleteOrderById(@PathVariable UUID orderId){
        orderService.deleteOrderById(orderId);
        return "Order deleted successfully";
    }
}
