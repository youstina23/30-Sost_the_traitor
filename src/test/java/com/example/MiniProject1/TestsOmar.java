package com.example.MiniProject1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.example.model.Cart;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.CartService;
import com.example.service.UserService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;

import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = "com.example.*")
@WebMvcTest
public class TestsOmar {



        @Value("${spring.application.userDataPath}")
        private String userDataPath;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private UserService userService;

        @Autowired
        private UserRepository userRepository;

        private UUID userId;
        private User testUser;
    @Autowired
    private CartService cartService;

    public void overrideAll() {
            try {
                objectMapper.writeValue(new File(userDataPath), new ArrayList<User>());
            } catch (IOException e) {
                throw new RuntimeException("Failed to write to JSON file", e);
            }
        }

        public User addUser(User user) {
            try {
                File file = new File(userDataPath);
                ArrayList<User> users;
                if (!file.exists()) {
                    users = new ArrayList<>();
                } else {
                    users = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, User[].class)));
                }
                users.add(user);
                objectMapper.writeValue(file, users);
                return user;
            } catch (IOException e) {
                throw new RuntimeException("Failed to write to JSON file", e);
            }
        }

        public ArrayList<User> getUsers() {
            try {
                File file = new File(userDataPath);
                if (!file.exists()) {
                    return new ArrayList<>();
                }
                return new ArrayList<>(Arrays.asList(objectMapper.readValue(file, User[].class)));
            } catch (IOException e) {
                throw new RuntimeException("Failed to read from JSON file", e);
            }
        }

        @BeforeEach
        void setUp() {
            userId = UUID.randomUUID();
            testUser = new User();
            testUser.setId(userId);
            testUser.setName("Test User");
            testUser.setOrders(new ArrayList<>());
            overrideAll();
        }


    @Test
    void testAddUserService() {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Test User");

        User savedUser = userService.addUser(testUser);

        assertNotNull(savedUser);
        assertEquals(testUser.getId(), savedUser.getId());
        assertEquals("Test User", savedUser.getName());
    }

    @Test
    void testAddUserWithNullName() {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName(null); // Invalid user

        assertThrows(BadRequestException.class, () -> userService.addUser(testUser));
    }

    @Test
    void testAddDuplicateUser() {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Omar");

        userService.addUser(testUser);

        assertThrows(BadRequestException.class, () -> userService.addUser(testUser));
    }

    @Test
    void testGetAllUsers1() {
        User testUser1 = new User(UUID.randomUUID(), "User1", new ArrayList<Order>());
        User testUser2 = new User(UUID.randomUUID(), "User2", new ArrayList<Order>());

        userService.addUser(testUser1);
        userService.addUser(testUser2);

        List<User> users = userService.getUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testGetAllUsers2() {
        List<User> users = userService.getUsers();
        assertEquals(0, users.size());
    }

    @Test
    void testGetAllUsers3() {
        UUID id = UUID.randomUUID();
        User testUser1 = new User(id, "User1", new ArrayList<Order>());
        userService.addUser(testUser1);


        userService.deleteUserById(id);

        List<User> users = userService.getUsers();
        assertEquals(0, users.size());
    }


    @Test
    void testGetUserByIdNotFound() {
        assertNull(userService.getUserById(UUID.randomUUID()));
    }

    @Test
    void testGetUserByIdService() {
        addUser(testUser);
        UUID id = testUser.getId();
        assertEquals(userService.getUserById(id), testUser, "User should be returned correctly according to ID");
    }

    @Test
    void testGetUserByIdInvalidFormat() {
        assertThrows(BadRequestException.class, () -> userService.getUserById(null), "Get User ID should not be accepting of null");
    }


    @Test
    void testGetOrdersByUserIdWithValidUser() {
        UUID userId = UUID.randomUUID();
        User testUser = new User(userId, "Test User", new ArrayList<>());
        Order order1 = new Order(UUID.randomUUID(), userId, 50.0, new ArrayList<>());
        Order order2 = new Order(UUID.randomUUID(), userId, 100.0, new ArrayList<>());

        testUser.setOrders(List.of(order1, order2));
        userService.addUser(testUser);

        List<Order> orders = userService.getOrdersByUserId(userId);

        assertNotNull(orders, "Orders list should not be null");
        assertEquals(2, orders.size(), "User should have exactly 2 orders");
        assertTrue(orders.contains(order1) && orders.contains(order2), "Orders should match the expected ones");
    }

    @Test
    void testGetOrdersByUserIdForUserWithNoOrders() {
        UUID userId = UUID.randomUUID();
        User testUser = new User(userId, "Test User", new ArrayList<>());

        userService.addUser(testUser);

        List<Order> orders = userService.getOrdersByUserId(userId);

        assertNotNull(orders, "Orders list should not be null");
        assertEquals(0, orders.size(), "User should have no orders");
    }

    @Test
    void testGetOrdersByUserIdForNonExistentUser() {
        UUID userId = UUID.randomUUID(); // Random user ID that doesn't exist

        assertThrows(NullPointerException.class, () -> userService.getOrdersByUserId(userId),
                "Fetching orders for a non-existent user should throw NotFoundException");
    }




    @Test
    void testAddOrderToUserWithValidCart() {
        UUID userId = UUID.randomUUID();
        User testUser = new User(userId, "Test User", new ArrayList<>());
        userService.addUser(testUser);

        Product product1 = new Product(UUID.randomUUID(), "Product 1", 25.0);
        Product product2 = new Product(UUID.randomUUID(), "Product 2", 75.0);

        Cart cart = new Cart(UUID.randomUUID(), userId, List.of(product1, product2));
        cartService.addCart(cart);

        userService.addOrderToUser(userId);

        List<Order> orders = userService.getOrdersByUserId(userId);
        assertNotNull(orders, "Orders list should not be null");
        assertEquals(1, orders.size(), "User should have exactly 1 order after checkout");
        assertEquals(100.0, orders.get(0).getTotalPrice(), "Order total should match the cart total");
        assertTrue(cartService.getCartByUserId(userId) == null, "Cart should be deleted after checkout");
    }

    @Test
    void testAddOrderToUserWithEmptyCart() {
        UUID userId = UUID.randomUUID();
        User testUser = new User(userId, "Test User", new ArrayList<>());
        userService.addUser(testUser);

        Cart emptyCart = new Cart(UUID.randomUUID(), userId, new ArrayList<>());
        cartService.addCart(emptyCart);

        Exception exception = assertThrows(RuntimeException.class, () -> userService.addOrderToUser(userId),
                "Adding an order with an empty cart should throw an exception");

        assertEquals("Cart empty", exception.getMessage(), "Exception message should indicate the cart is empty");
    }

    @Test
    void testAddOrderToUserWithNoCart() {
        UUID userId = UUID.randomUUID();
        User testUser = new User(userId, "Test User", new ArrayList<>());
        userService.addUser(testUser);

        Exception exception = assertThrows(RuntimeException.class, () -> userService.addOrderToUser(userId),
                "Adding an order for a user with no cart should throw an exception");

        assertEquals("Cart not found", exception.getMessage(), "Exception message should indicate the cart is missing");
    }


    @Test
    void testEmptyCartWithProducts() {
        UUID userId = UUID.randomUUID();
        User testUser = new User(userId, "Test User", new ArrayList<>());
        userService.addUser(testUser);

        Product product1 = new Product(UUID.randomUUID(), "Product 1", 20.0);
        Product product2 = new Product(UUID.randomUUID(), "Product 2", 40.0);

        Cart cart = new Cart(UUID.randomUUID(), userId, new ArrayList<>(List.of(product1, product2)));
        cartService.addCart(cart);

        userService.emptyCart(userId);

        Cart updatedCart = cartService.getCartByUserId(userId);
        assertNotNull(updatedCart, "Cart should still exist after emptying");
        assertEquals(0, updatedCart.getProducts().size(), "Cart should have no products after emptying");
    }

    @Test
    void testEmptyCartWhenAlreadyEmpty() {
        UUID userId = UUID.randomUUID();
        User testUser = new User(userId, "Test User", new ArrayList<>());
        userService.addUser(testUser);

        Cart emptyCart = new Cart(UUID.randomUUID(), userId, new ArrayList<>());
        cartService.addCart(emptyCart);

        userService.emptyCart(userId);

        Cart updatedCart = cartService.getCartByUserId(userId);
        assertNotNull(updatedCart, "Cart should still exist after emptying");
        assertEquals(0, updatedCart.getProducts().size(), "Cart should remain empty after calling emptyCart");
    }

    @Test
    void testEmptyCartForNonExistentCart() {
        UUID userId = UUID.randomUUID();
        User testUser = new User(userId, "Test User", new ArrayList<>());
        userService.addUser(testUser);

        Exception exception = assertThrows(RuntimeException.class, () -> userService.emptyCart(userId),
                "Attempting to empty a non-existent cart should throw an exception");

        assertEquals("Cart not found", exception.getMessage(), "Exception message should indicate cart is missing");
    }


    @Test
    void testRemoveOrderFromUserWithValidOrder() {
        UUID userId = UUID.randomUUID();
        User testUser = new User(userId, "Test User", new ArrayList<>());
        userService.addUser(testUser);

        Order order = new Order(UUID.randomUUID(), userId, 100.0, new ArrayList<>());
        userService.getOrdersByUserId(userId).add(order);
        userRepository.addOrderToUser(userId, order); // Manually adding order to simulate database

        userService.removeOrderFromUser(userId, order.getId());

        List<Order> ordersAfterRemoval = userService.getOrdersByUserId(userId);
        assertNotNull(ordersAfterRemoval, "Orders list should not be null");
        assertEquals(0, ordersAfterRemoval.size(), "Order should be removed from user");
    }

    @Test
    void testRemoveOrderFromUserWithNonExistentOrder() {
        UUID userId = UUID.randomUUID();
        User testUser = new User(userId, "Test User", new ArrayList<>());
        userService.addUser(testUser);

        UUID nonExistentOrderId = UUID.randomUUID();

        Exception exception = assertThrows(RuntimeException.class,
                () -> userService.removeOrderFromUser(userId, nonExistentOrderId),
                "Attempting to remove an order that doesn't exist should throw an exception");

        assertEquals("Order not found", exception.getMessage(), "Exception message should indicate the order is missing");
    }

    @Test
    void testRemoveOrderFromNonExistentUser() {
        UUID nonExistentUserId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        Exception exception = assertThrows(RuntimeException.class,
                () -> userService.removeOrderFromUser(nonExistentUserId, orderId),
                "Attempting to remove an order from a non-existent user should throw an exception");
    }


    @Test
    void testDeleteExistingUser() {
        UUID userId = UUID.randomUUID();
        User testUser = new User(userId, "Test User", new ArrayList<>());
        userService.addUser(testUser);

        userService.deleteUserById(userId);

        assertNull(userService.getUserById(userId),
                "Fetching a deleted user should equal null");
    }

    @Test
    void testDeleteNonExistentUser() {
        UUID nonExistentUserId = UUID.randomUUID();

        Exception exception = assertThrows(RuntimeException.class,
                () -> userService.deleteUserById(nonExistentUserId),
                "Deleting a non-existent user should throw an exception");

        assertEquals("User not found", exception.getMessage(), "Exception message should indicate user does not exist");
    }

    @Test
    void testDeleteUserWithOrders() {
        UUID userId = UUID.randomUUID();
        User testUser = new User(userId, "Test User", new ArrayList<>());
        userService.addUser(testUser);

        Order order = new Order(UUID.randomUUID(), userId, 50.0, new ArrayList<>());
        userService.getOrdersByUserId(userId).add(order);
        userRepository.addOrderToUser(userId, order); // Simulating adding an order to the user

        userService.deleteUserById(userId);

        assertNull(userService.getUserById(userId),
                "Fetching a deleted user should equal null");

        assertThrows(NullPointerException.class, () -> userService.getOrdersByUserId(userId), "Orders should be removed along with the user");
    }





}
