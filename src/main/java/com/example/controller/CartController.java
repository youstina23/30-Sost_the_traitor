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
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @PostMapping("/")
    public Cart addCart(@RequestBody Cart cart) {
        return cartService.addCart(cart);
    }

    @GetMapping("/")
    public List<Cart> getCarts() {
        return cartService.getCarts();
    }
    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable UUID id) {
        return cartService.getCartById(id);
    }

    @PutMapping("/addProduct/{id}")
    public String addProductToCart(@PathVariable UUID id, @RequestBody Product product) {
        try{
            cartService.addProductToCart(id, product);
            return "Product added to cart successfully";
        }catch(Exception e){
            return "Cart not found";
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public String deleteProductFromCart(@PathVariable UUID id, @RequestBody Product product) {
        try{
            cartService.deleteProductFromCart(id, product);
            return "Product deleted from cart successfully";
        }catch(Exception e){
            return "Cart not found";
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteCartById(@PathVariable UUID id) {
        try{
            cartService.deleteCartById(id);
            return "Cart deleted successfully";
        }catch(Exception e){
            return "Cart not found";
        }
    }
}
