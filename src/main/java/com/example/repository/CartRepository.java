package com.example.repository;

import com.example.model.Cart;
import com.example.model.Product;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@SuppressWarnings("rawtypes")
public class CartRepository extends MainRepository<Cart> {

//    private static final String DATA_PATH = System.getenv("CART_DATA_PATH");
private static final String DATA_PATH = "src/main/java/com/example/data/carts.json";

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
        return findAll().stream()
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
        Cart cart = carts.stream()
                .filter(c -> c.getId().equals(cartId))
                .findFirst()
                .orElse(null);

        if (cart != null) {
            cart.getProducts().add(product);
            saveAll(carts);
        }
    }



    public void deleteProductFromCart(UUID cartId, Product product) {
        ArrayList<Cart> carts = findAll();

        Cart cart = carts.stream()
                .filter(c -> c.getId().equals(cartId))
                .findFirst()
                .orElse(null);

        if (cart != null) {

            cart.getProducts().removeIf(p -> p.getId().equals(product.getId()));

            saveAll(carts);
        }
    }

    public void deleteCartById(UUID cartId) {
        delete(cartId);
    }







}