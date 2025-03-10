package com.example.service;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class UserService extends MainService<User>{

    OrderService orderService;
    UserRepository userRepository;
    CartService cartService;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy CartService cartService, OrderService orderService) {
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.orderService = orderService;
    }


    public User addUser(User user) {
        if (userRepository.getUserById(user.getId())==null){
            return userRepository.addUser(user);
        }else throw new IllegalArgumentException("User ID is duplicated");

    }

    public ArrayList<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUserById(UUID userId) {
        if(userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        User u = userRepository.getUserById(userId);
        return u;
    }

    public List<Order> getOrdersByUserId(UUID userId) {
        return userRepository.getOrdersByUserId(userId);
    }


    public void addOrderToUser(UUID userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        if (cart.getProducts().isEmpty()) {
            throw new RuntimeException("Cart empty");
        }

        double totalPrice = 0;
        for (Product product : cart.getProducts()) {
            totalPrice += product.getPrice();
        }

        Order order = new Order(userId, totalPrice, cart.getProducts());

        orderService.addOrder(order);

        cartService.deleteCartById(cart.getId());

        userRepository.addOrderToUser(userId, order);
    }

    public void emptyCart(UUID userId){
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        for (Product product : new ArrayList<>(cart.getProducts())) {
            cartService.deleteProductFromCart(cart.getId(), product);
        }
    };

    public void removeOrderFromUser(UUID userId, UUID orderId) {
        userRepository.removeOrderFromUser(userId, orderId);
    }

    public void deleteUserById(UUID userId) {
        User user = userRepository.getUserById(userId);
        if(user == null) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteUserById(userId);
    }

}