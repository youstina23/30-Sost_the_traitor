package com.example.repository;

import com.example.model.Order;
import com.example.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class OrderRepository extends MainRepository<Order> {
    private static final String DATA_PATH = "./data/orders.json";
    private final Order order;

    public OrderRepository(Order order) {
        this.order = order;
    }

    @Override
    protected String getDataPath() {
        return "";
    }

    @Override
    protected Class<Order[]> getArrayType() {
        return null;
    }

    public void addOrder(Order order){
        save(order);

    }
    public ArrayList<Order> getOrders(){
        return findAll();
    }

    public Order getOrderById(UUID orderId){
        return findAll().stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }
    public void deleteOrderById(UUID orderId){

        ArrayList<Order> orders = findAll();
        orders.removeIf(order-> order.getId().equals(orderId));
        saveAll(orders);
    }
}
