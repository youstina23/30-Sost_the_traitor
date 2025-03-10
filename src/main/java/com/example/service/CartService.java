package com.example.service;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.repository.CartRepository;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class CartService extends MainService<Cart> {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    public CartService(CartRepository cartRepository, ProductService productService, UserService userService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public Cart addCart(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }
        if (this.getCartByUserId(cart.getUserId()) != null) {
            throw new IllegalArgumentException("A cart already exists for this user");
        }
        if (userService.getUserById(cart.getUserId())==null){
            throw new IllegalArgumentException("This user does not exist");
        }
        return cartRepository.addCart(cart);
    }

    public ArrayList<Cart> getCarts() {
        return cartRepository.getCarts();
    }


    public Cart getCartById(UUID cartId) {
        if (cartId == null) {
            throw new IllegalArgumentException("Cart ID cannot be null");
        }
        return cartRepository.getCartById(cartId);
    }


    public Cart getCartByUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return cartRepository.getCartByUserId(userId);
    }


    public void addProductToCart(UUID cartId, Product product) {
        Cart cart = cartRepository.getCartById(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        Product p = productService.findProductByNameAndPrice(product.getName(), product.getPrice());

        if (p == null) {
            Product pnew = productService.addProduct(product);
            cartRepository.addProductToCart(cartId, pnew);
        }else{
            cartRepository.addProductToCart(cartId, p);
        }

    }


    public void deleteProductFromCart(UUID cartId, Product product) {
        Cart cart = cartRepository.getCartById(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        cartRepository.deleteProductFromCart(cartId, product);
    }


    public void deleteCartById(UUID cartId) {
        cartRepository.deleteCartById(cartId);
    }

}
