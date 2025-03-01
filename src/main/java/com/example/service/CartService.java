package com.example.service;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class CartService extends MainService<Cart> {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // Add New Cart
    public Cart addCart(Cart cart) {
        return cartRepository.addCart(cart);
    }

    // Get All Carts
    public ArrayList<Cart> getCarts() {
        return cartRepository.getCarts();
    }

    // Get Cart by ID
    public Cart getCartById(UUID cartId) {
        return cartRepository.getCartById(cartId);
    }

    // Get Cart by User ID
    public Cart getCartByUserId(UUID userId) {
        return cartRepository.getCartByUserId(userId);
    }

    // Add Product to Cart
    public void addProductToCart(UUID cartId, Product product) {
        cartRepository.addProductToCart(cartId, product);
    }

    // Delete Product from Cart
    public void deleteProductFromCart(UUID cartId, Product product) {
        cartRepository.deleteProductFromCart(cartId, product);
    }

    // Delete Cart by ID
    public void deleteCartById(UUID cartId) {
        cartRepository.deleteCartById(cartId);
    }
}
