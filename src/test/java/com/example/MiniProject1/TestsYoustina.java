package com.example.MiniProject1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import com.example.service.CartService;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = "com.example.*")
@WebMvcTest
public class TestsYoustina {

     @Value("${spring.application.cartDataPath}")
     private String cartDataPath;

 	@Autowired
 	private MockMvc mockMvc;

 	@Autowired
 	private ObjectMapper objectMapper;

 	@Autowired
 	private CartService cartService;
 	@Autowired
 	private CartRepository cartRepository;


 	public void overRideAll(){
         try{
             objectMapper.writeValue(new File(cartDataPath), new ArrayList<Cart>());
         } catch (IOException e) {
             throw new RuntimeException("Failed to write to JSON file", e);
         }
     }

     public Cart addCart(Cart cart){
        try{
               File file = new File(cartDataPath);
               ArrayList<Cart> carts;
               if (!file.exists()) {
                 carts = new ArrayList<>();
               }
               else {
                 carts = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Cart[].class)));
               }
               carts.add(cart);
               objectMapper.writeValue(file, carts);
               return cart;
          } catch (IOException e) {
               throw new RuntimeException("Failed to write to JSON file", e);
        }
     }
     public ArrayList<Cart> getCarts() {
          try {
               File file = new File(cartDataPath);
               if (!file.exists()) {
                 return new ArrayList<>();
               }
               return new ArrayList<Cart>(Arrays.asList(objectMapper.readValue(file, Cart[].class)));
          } catch (IOException e) {
               throw new RuntimeException("Failed to read from JSON file", e);
          }
     }

    private UUID userId;
    private UUID cartId;
    private Cart testCart;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        cartId = UUID.randomUUID();
        testCart = new Cart();
        testCart.setId(cartId);
        testCart.setUserId(userId);
        testCart.setProducts(new ArrayList<>());
        overRideAll();
    }

    @Test
    void addCart_validCart_cartAdded() throws Exception {
        // Arrange
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        cart.setProducts(new ArrayList<>());

        // Act
        cartService.addCart(cart);

        // Assert
        boolean found = false;
        for (Cart storedCart : getCarts()) {
            if (storedCart.getId().equals(cartId)) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Cart should be added correctly");
    }

    @Test
    void addCart_cartWithProducts_productsPersisted() {
        // Arrange
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        Product product  = new Product(UUID.randomUUID(), "Gebna", 15.0);
        List<Product> updatedProducts = new ArrayList<>(cart.getProducts());
        updatedProducts.add(product);
        cart.setProducts(updatedProducts);

        // Act
        Cart addedCart = cartService.addCart(cart);

        // Assert
        assertNotNull(addedCart, "The returned cart should not be null");
        assertEquals(1, addedCart.getProducts().size(), "The cart should contain exactly one product");
        assertEquals("Gebna", addedCart.getProducts().get(0).getName(), "The product name should match");
    }

    @Test
    void addCart_nullCart_nullException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> cartService.addCart(null));
        assertEquals("Cart cannot be null", exception.getMessage(), "Exception message should match");
    }

    @Test
    void getCarts_manyCarts_allCarts() {
        // Arrange
        Cart cart1 = new Cart();
        cart1.setId(UUID.randomUUID());
        cart1.setUserId(userId);

        Cart cart2 = new Cart();
        cart2.setId(UUID.randomUUID());
        cart2.setUserId(UUID.randomUUID());

        cartService.addCart(cart1);
        cartService.addCart(cart2);

        System.out.println("Stored carts: " + cartService.getCarts());

        // Act
        ArrayList<Cart> carts = cartService.getCarts();

        // Assert
        assertNotNull(carts, "Returned carts list should not be null");
        assertEquals(2, carts.size(), "Should return exactly two carts");
    }

    @Test
    void getCarts_noCarts_emptyList() {
        ArrayList<Cart> carts = cartService.getCarts();
        assertNotNull(carts, "Returned list should not be null");
        assertTrue(carts.isEmpty(), "Should return an empty list when no carts exist");
    }

    @Test
    void getCarts_afterDeletingAllCarts_returnEmptyList() {
        // Arrange
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);

        cartRepository.addCart(cart);
        cartService.deleteCartById(cartId);

        // Act
        ArrayList<Cart> carts = cartService.getCarts();

        // Assert
        assertNotNull(carts, "Returned list should not be null");
        assertTrue(carts.isEmpty(), "Should return an empty list after deleting all carts");
    }


    @Test
    void getCartById_existingCart_cartFound() {
        // Arrange
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        cartRepository.addCart(cart);

        // Act
        Cart retCart = cartService.getCartById(cartId);

        // Assert
        assertNotNull(retCart, "The retrieved cart should not be null");
        assertEquals(cartId, retCart.getId(), "The cart ID should match the requested ID");
    }

    @Test
    void getCartById_nonExistentCart_returnsNull() {
        Cart cart = cartService.getCartById(UUID.randomUUID());
        assertNull(cart, "Should return null if the cart does not exist");
    }

    @Test
    void getCartById_nullId_nullException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> cartService.getCartById(null));
        assertEquals("Cart ID cannot be null", exception.getMessage(), "Exception message should match");
    }

    @Test
    void getCartByUserId_existUser_cartRetrieved() {
        // Arrange
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        cartRepository.addCart(cart);

        // Act
        Cart retCart = cartService.getCartByUserId(userId);

        // Assert
        assertNotNull(retCart, "The retrieved cart should not be null");
        assertEquals(userId, retCart.getUserId(), "The cart's user ID should match the requested ID");
    }

    @Test
    void getCartByUserId_nonUser_null() {
        Cart retCart = cartService.getCartByUserId(UUID.randomUUID());
        assertNull(retCart, "Should return null if no cart exists for the given user ID");
    }

    @Test
    void getCartByUserId_nullUserId_nullException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> cartService.getCartByUserId(null));
        assertEquals("User ID cannot be null", exception.getMessage(), "Exception message should match");
    }

    @Test
    void addProductToCart_existCart_productAdded() {
        // Arrange
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        cartRepository.addCart(cart);

        Product product = new Product(UUID.randomUUID(), "Gebna", 10.0);

        // Act
        cartService.addProductToCart(cartId, product);

        // Assert
        Cart updatedCart = cartRepository.getCartById(cartId);
        assertNotNull(updatedCart, "Cart should exist");
        assertEquals(1, updatedCart.getProducts().size(), "Cart should contain exactly one product");
        assertEquals("Gebna", updatedCart.getProducts().get(0).getName(), "Product name should match");
    }

    @Test
    void addProductToCart_nonCart_nullException() {
        UUID randomId = UUID.randomUUID();
        Product product = new Product(UUID.randomUUID(), "Gebna", 50.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> cartService.addProductToCart(randomId, product));
        assertEquals("Cart not found", exception.getMessage(), "Exception message should match");
    }

    @Test
    void addProductToCart_nullProduct_throwsException() {
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        cartRepository.addCart(cart);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> cartService.addProductToCart(cartId, null));
        assertEquals("Product cannot be null", exception.getMessage(), "Exception message should match");
    }


    @Test
    void deleteProductFromCart_existCart_productRemoved() {
        // Arrange
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        Product product = new Product(UUID.randomUUID(), "Gebna", 10.0);
        cart.setProducts(new ArrayList<>(List.of(product)));
        cartRepository.addCart(cart);

        // Act
        cartService.deleteProductFromCart(cartId, product);

        // Assert
        Cart updatedCart = cartRepository.getCartById(cartId);
        assertNotNull(updatedCart, "Cart should exist");
        assertTrue(updatedCart.getProducts().isEmpty(), "Cart should be empty after product removal");
    }

    @Test
    void deleteProductFromCart_productNotInCart_noChange() {
        // Arrange
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        cartRepository.addCart(cart);

        Product product = new Product(UUID.randomUUID(), "Gebna", 10.0);

        // Act
        cartService.deleteProductFromCart(cartId, product);

        // Assert
        Cart updatedCart = cartRepository.getCartById(cartId);
        assertNotNull(updatedCart, "Cart should exist");
        assertTrue(updatedCart.getProducts().isEmpty(), "Cart should still be empty");
    }

    @Test
    void deleteProductFromCart_nonCart_throwsException() {
        UUID randomId = UUID.randomUUID();
        Product product = new Product(UUID.randomUUID(), "Gebna", 50.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> cartService.deleteProductFromCart(randomId, product));
        assertEquals("Cart not found", exception.getMessage(), "Exception message should match");
    }

    @Test
    void deleteCartById_existCart_cartDeleted() {
        // Arrange
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        cartRepository.addCart(cart);

        // Act
        cartService.deleteCartById(cartId);

        // Assert
        Cart deletedCart = cartRepository.getCartById(cartId);
        assertNull(deletedCart, "Cart should be deleted and return null");
    }

    @Test
    void deleteCartById_nonCart_noEffect() {
        UUID randomId = UUID.randomUUID();
        assertDoesNotThrow(() -> cartService.deleteCartById(randomId), "Deleting a non-existent cart should not throw an error");
    }

    @Test
    void deleteCartById_cartWithProducts_cartDeleted() {
        // Arrange
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(userId);
        cart.setProducts(new ArrayList<>(List.of(new Product(UUID.randomUUID(), "Gebna", 5.0))));
        cartRepository.addCart(cart);

        // Act
        cartService.deleteCartById(cartId);

        // Assert
        Cart deletedCart = cartRepository.getCartById(cartId);
        assertNull(deletedCart, "Cart with products should be deleted successfully");
    }


}
