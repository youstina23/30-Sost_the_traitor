package com.example.service;

import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Order;
import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    

    public User addUser(User user) {
       return userRepository.addUser(user);
    }
    public List<User> getUsers() {
        return userRepository.getUsers();
    }
    public User getUserById(UUID id) {
        return userRepository.getUserById(id);
    }

    public List<Order> getOrdersByUserId(UUID id) {
        return userRepository.getOrdersByUserId(id);
    }

    public void addOrderToUser(UUID id, Order order) throws IllegalArgumentException {
        userRepository.addOrderToUser(id, order);
    }

    public void deleteUserById(UUID id) {
        userRepository.deleteUserById(id);
    }

    
}
