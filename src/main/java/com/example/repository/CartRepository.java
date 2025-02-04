package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.Cart;
import com.example.model.Product;

@Component
public class CartRepository {

    public static List<Cart> carts = new ArrayList<>();

    public CartRepository(){
        
    }



    public Cart addCart(Cart cart) {
        carts.add(cart);
        return cart;
    }

    public List<Cart> getCarts() {
        return carts;
    }
    public Cart getCartById(UUID id){
        for (Cart cart : carts) {
            if (cart.getId().equals(id)) {
                return cart;
            }
        }
        return null;
    }

    public Cart getCartByUserId(UUID id){
        for (Cart cart : carts) {
            if (cart.getUserId().equals(id)) {
                return cart;
            }
        }
        Cart cart = new Cart();
        cart.setId(UUID.randomUUID());
        cart.setUserId(id);
        carts.add(cart);
        return cart;
    }

    public void addProductToCart(UUID id, Product product) {
        for (Cart cart : carts) {
            if (cart.getId().equals(id)) {
                cart.getProducts().add(product);
                return;
            }
        }
        throw new IllegalArgumentException("Cart not found");
    }
    public void deleteProductFromCart(UUID id, Product product) {
        for (Cart cart : carts) {
            if (cart.getId().equals(id)) {
                cart.getProducts().remove(product);
                return;
            }
        }
        throw new IllegalArgumentException("Cart not found");
    }

    public void deleteCartById(UUID id) throws IllegalArgumentException {
        for (Cart cart : carts) {
            if (cart.getId().equals(id)) {
                carts.remove(cart);
                return;
            }
        }
        throw new IllegalArgumentException("Cart not found");
    }
    
    
    
}
