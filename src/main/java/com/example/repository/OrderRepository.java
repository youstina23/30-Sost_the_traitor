package com.example.repository;

import com.example.model.Order;
import com.example.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class OrderRepository extends MainRepository<Order> {
    private static final String DATA_PATH = System.getenv("ORDER_DATA_PATH") != null
            ? System.getenv("ORDER_DATA_PATH")
            : "src/main/java/com/example/data/orders.json";


    public OrderRepository() {}

    @Override
    protected String getDataPath() {
        return DATA_PATH;
    }

    @Override
    protected Class<Order[]> getArrayType() {
        return Order[].class;
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
        delete(orderId);
    }
}
