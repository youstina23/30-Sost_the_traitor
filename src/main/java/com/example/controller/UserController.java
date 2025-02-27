package com.example.controller;

import com.example.model.User;
import com.example.model.Order;
import com.example.service.UserService;
import com.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/")
    public ArrayList<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/{userId}/orders")
    public List<Order> getOrdersByUserId(@PathVariable UUID userId) {
        return userService.getOrdersByUserId(userId);
    }

//    @PostMapping("/{userId}/checkout")
//    public String addOrderToUser(@PathVariable UUID userId) {
//        return userService.addOrderToUser(userId);
//    }

    @PostMapping("/{userId}/removeOrder")
    public String removeOrderFromUser(@PathVariable UUID userId, @RequestParam UUID orderId) {
        userService.removeOrderFromUser(userId, orderId);
        return "Order removed";
    }

//    @DeleteMapping("/{userId}/emptyCart")
//    public String emptyCart(@PathVariable UUID userId) {
//        cartService.clearCart(userId);
//        return "Cart emptied";
//    }

//    @PutMapping("/addProductToCart")
//    public String addProductToCart(@RequestParam UUID userId, @RequestParam UUID productId) {
//        cartService.addProductToCart(userId, productId);
//        return "Product added to cart";
//    }

//    @PutMapping("/deleteProductFromCart")
//    public String deleteProductFromCart(@RequestParam UUID userId, @RequestParam UUID productId) {
//        cartService.removeProductFromCart(userId, productId);
//        return "Product removed from cart.";
//    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUserById(@PathVariable UUID userId) {
        userService.deleteUserById(userId);
        return "User deleted";
    }
}
