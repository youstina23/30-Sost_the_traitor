package com.example.controller;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.Order;
import com.example.service.ProductService;
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

    ProductService productService;
    UserService userService;
    CartService cartService;
    @Autowired
    public UserController(UserService userService, CartService cartService, ProductService productService) {
        this.userService = userService;
        this.cartService = cartService;
        this.productService = productService;
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

    @PostMapping("/{userId}/checkout")
    public String addOrderToUser(@PathVariable UUID userId) {
        userService.addOrderToUser(userId);
        return "Order added to user";
    }

    @PostMapping("/{userId}/removeOrder")
    public String removeOrderFromUser(@PathVariable UUID userId, @RequestParam UUID orderId) {
        userService.removeOrderFromUser(userId, orderId);
        return "Order removed from user";
    }

    @DeleteMapping("/{userId}/emptyCart")
    public String emptyCart(@PathVariable UUID userId) {
        userService.emptyCart(userId);
        return "Cart emptied";
    }

    @PutMapping("/{userId}/addProductToCart")
    public String addProductToCart(@PathVariable UUID userId, @RequestBody UUID productId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        Product product = productService.getProductById(productId);
        cartService.addProductToCart(cart.getId(), product);
        return "Product added to cart";
    }

    @PutMapping("/{userId}/deleteProductFromCart")
    public String deleteProductFromCart(@PathVariable UUID userId, @RequestBody UUID productId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        Product product = productService.getProductById(productId);
        cartService.deleteProductFromCart(cart.getId(), product);
        return "Product removed from cart.";
    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUserById(@PathVariable UUID userId) {
        userService.deleteUserById(userId);
        return "User deleted";
    }
}
