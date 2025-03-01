package com.example.repository;

import com.example.model.Cart;
import com.example.model.Product;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CartRepository extends MainRepository<Cart> {

    private static final String DATA_PATH = "./data/carts.json";

    @Override
    protected String getDataPath() {
        return DATA_PATH;
    }

    @Override
    protected Class<Cart[]> getArrayType() {
        return Cart[].class;
    }

    public CartRepository() {
    }


    public Cart addCart(Cart cart) {
        save(cart);
        return cart;
    }

    public ArrayList<Cart> getCarts() {
        return findAll();
    }

    public Cart getCartById(UUID cartId) {
        return findAll().stream() // Reads from carts.json
                .filter(cart -> cart.getId().equals(cartId))
                .findFirst()
                .orElse(null);
    }


    public Cart getCartByUserId(UUID userId) {
        return findAll().stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public void addProductToCart(UUID cartId, Product product) {
        ArrayList<Cart> carts = findAll();

        // Find the cart by ID
        Cart cart = carts.stream()
                .filter(c -> c.getId().equals(cartId))
                .findFirst()
                .orElse(null);

        if (cart != null) {
            // Add the new product to the cart's product list
            cart.getProducts().add(product);
            saveAll(carts); // Save updated carts list to JSON file
        }
    }



    public void deleteProductFromCart(UUID cartId, Product product) {
        ArrayList<Cart> carts = findAll();

        // Find the cart by ID
        Cart cart = carts.stream()
                .filter(c -> c.getId().equals(cartId))
                .findFirst()
                .orElse(null);

        if (cart != null) {
            // Remove the product from the cart's product list
            cart.getProducts().removeIf(p -> p.getId().equals(product.getId()));

            // Save the updated cart list
            saveAll(carts);
        }
    }

    public void deleteCartById(UUID cartId) {
        ArrayList<Cart> carts = findAll();

        // Remove the cart with the matching ID
        carts.removeIf(cart -> cart.getId().equals(cartId));

        // Save the updated carts list back to JSON
        saveAll(carts);
    }







}