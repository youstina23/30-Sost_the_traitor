package com.example.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.service.CartService;
import com.example.service.ProductService;
import com.example.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/orders")
    public List<Order> getOrdersByUserId(@PathVariable UUID id) {
        return userService.getOrdersByUserId(id);
    }

    @PostMapping("/{id}/checkout")
    public String addOrderToUser(@PathVariable UUID id) {
        Cart userCart=cartService.getCartByUserId(id);
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserId(id);
        order.setProducts(userCart.getProducts());
        double totalPrice=0;
        for(Product product: userCart.getProducts()){
            totalPrice+=product.getPrice();
        }
        order.setTotalPrice(totalPrice);
        try{
            userService.addOrderToUser(id, order);
            return "Order added successfully";
        }catch(Exception e){
            return "Order not added User Id not found";

        }
    }

    @PutMapping("/addProductToCart")
    public String addProductToCart(@RequestParam UUID userId, @RequestParam UUID productId) {
        Product product =productService.getProductById(productId);
        Cart cart=cartService.getCartByUserId(userId);
        try{
            cartService.addProductToCart(cart.getId(), product);
            return "Product added to cart";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "Product not added to cart";
        }
        
    }

    @DeleteMapping("/deleteProductFromCart")
    public String deleteProductFromCart(@RequestParam UUID userId, @RequestParam UUID productId) {
        Product product =productService.getProductById(productId);
        Cart cart=cartService.getCartByUserId(userId);
        if(cart.getProducts().size()==0){
            return "Cart is empty";
        }
        
        try{
            cartService.deleteProductFromCart(cart.getId(), product);
            return "Product deleted from cart";
        }catch(Exception e){
            return "Product not deleted from cart";
        }
        
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUserById(@PathVariable UUID id) {
        try{
            userService.deleteUserById(id);
            return "User deleted successfully";

        }catch(Exception e){
            return "User not found";
        }
    }


    
    
}
