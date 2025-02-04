package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.model.Order;
import com.example.model.User;

@Component
public class UserRepository {

    public static List<User> users = new ArrayList<>();
    public UserRepository() {
        
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserById(UUID id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
    public User addUser(User user) {
        users.add(user);
        return user;
    }
   
    public List<Order> getOrdersByUserId(UUID id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user.getOrders();
            }
        }
        return null;
    }

    public void addOrderToUser(UUID id, Order order) throws IllegalArgumentException {
        for (User user : users) {
            if (user.getId().equals(id)) {
                user.getOrders().add(order);
                return;
            }
        }
        throw new IllegalArgumentException("User not found");
    }

    public void deleteUserById(UUID id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                users.remove(user);
                return;
            }
        }
        throw new IllegalArgumentException("User not found");
    }
        
    
}
