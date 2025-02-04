package com.example.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart addCart(Cart cart) {
        return cartRepository.addCart(cart);
    }

    public List<Cart> getCarts() {
        return cartRepository.getCarts();
    }
    public Cart getCartById(UUID id) {
        return cartRepository.getCartById(id);
    }
    public Cart getCartByUserId(UUID id) {
        return cartRepository.getCartByUserId(id);
    }

    public void addProductToCart(UUID id, Product product) {
        cartRepository.addProductToCart(id, product);
    }
    public void deleteProductFromCart(UUID id, Product product) {
        cartRepository.deleteProductFromCart(id, product);
    }

    public void deleteCartById(UUID id) {
        cartRepository.deleteCartById(id);
    }
    
    
}
