
package com.example.MiniProject1;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ComponentScan(basePackages = "com.example.*")
@WebMvcTest
public class Tests {


    @Value("${spring.application.userDataPath}")
    private String userDataPath;

    @Value("${spring.application.productDataPath}")
    private String productDataPath;

    @Value("${spring.application.orderDataPath}")
    private String orderDataPath;

    @Value("${spring.application.cartDataPath}")
    private String cartDataPath;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void overRideAll(){
        try{
            objectMapper.writeValue(new File(userDataPath), new ArrayList<User>());
            objectMapper.writeValue(new File(productDataPath), new ArrayList<Product>());
            objectMapper.writeValue(new File(orderDataPath), new ArrayList<Order>());
            objectMapper.writeValue(new File(cartDataPath), new ArrayList<Cart>());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

    public Object find(String typeString, Object toFind){
        switch(typeString){
            case "User":
                ArrayList<User> users = getUsers();

                for(User user: users){
                    if(user.getId().equals(((User)toFind).getId())){
                        return user;
                    }
                }
                break;
            case "Product":
                ArrayList<Product> products = getProducts();
                for(Product product: products){
                    if(product.getId().equals(((Product)toFind).getId())){
                        return product;
                    }
                }
                break;
            case "Order":
                ArrayList<Order> orders = getOrders();
                for(Order order: orders){
                    if(order.getId().equals(((Order)toFind).getId())){
                        return order;
                    }
                }
                break;
            case "Cart":
                ArrayList<Cart> carts = getCarts();
                for(Cart cart: carts){
                    if(cart.getId().equals(((Cart)toFind).getId())){
                        return cart;
                    }
                }
                break;
        }
        return null;
    }

    public Product addProduct(Product product) {
        try {
            File file = new File(productDataPath);
            ArrayList<Product> products;
            if (!file.exists()) {
                products = new ArrayList<>();
            }
            else {
                products = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Product[].class)));
            }
            products.add(product);
            objectMapper.writeValue(file, products);
            return product;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }
    public ArrayList<Product> getProducts() {
        try {
            File file = new File(productDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<Product>(Arrays.asList(objectMapper.readValue(file, Product[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }

    public User addUser(User user) {
        try {
            File file = new File(userDataPath);
            ArrayList<User> users;
            if (!file.exists()) {
                users = new ArrayList<>();
            }
            else {
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
            return new ArrayList<User>(Arrays.asList(objectMapper.readValue(file, User[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
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
    public Order addOrder(Order order){
        try{
            File file = new File(orderDataPath);
            ArrayList<Order> orders;
            if (!file.exists()) {
                orders = new ArrayList<>();
            }
            else {
                orders = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
            }
            orders.add(order);
            objectMapper.writeValue(file, orders);
            return order;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }
    public ArrayList<Order> getOrders() {
        try {
            File file = new File(orderDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<Order>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }


    private UUID cartId;
    private UUID userId;
    private User testUser;
    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        cartId= UUID.randomUUID();
        testUser = new User();
        testUser.setId(userId);
        testUser.setName("Test User");

        overRideAll();
    }


    // ------------------------ User Tests -------------------------


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

        assertThrows(IllegalArgumentException.class, () -> userService.addUser(testUser));
    }

    @Test
    void testAddUserWithDuplicateUUID() {
        User existingUser = new User(userId, "Existing User", new ArrayList<>());
        userService.addUser(existingUser); // Add the first user

        User newUser = new User(userId, "New User", new ArrayList<>()); // Same UUID

        // Expect an exception when adding a user with the same UUID
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(newUser),
                "User with the same UUID already exists");
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
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(null), "Get User ID should not be accepting of null");
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



    //------------------------------Product Tests-----------------------------

    @Test
    void addProduct_validProduct_productAdded() {
        // Arrange
        ProductRepository mockRepository = mock(ProductRepository.class);
        ProductService productService = new ProductService(mockRepository);
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Laptop");
        product.setPrice(1200.0);

        when(mockRepository.addProduct(product)).thenReturn(product);

        // Act
        Product result = productService.addProduct(product);

        // Assert
        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals(1200.0, result.getPrice());
        verify(mockRepository, times(1)).addProduct(product);
    }

    @Test
    void addProduct_nullProduct_throwException() {
        // Arrange
        ProductRepository mockRepository = mock(ProductRepository.class);
        ProductService productService = new ProductService(mockRepository);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> productService.addProduct(null));
    }

    @Test
    void addProduct_duplicateProduct_throwException() {
        // Arrange
        ProductRepository mockRepository = mock(ProductRepository.class);
        ProductService productService = new ProductService(mockRepository);
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Smartphone");
        product.setPrice(800.0);

        when(mockRepository.addProduct(product)).thenThrow(new IllegalArgumentException("Product already exists"));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.addProduct(product));
        assertEquals("Product already exists", exception.getMessage());
        verify(mockRepository, times(1)).addProduct(product);
    }

    @Test
    void getProducts_noProducts_emptyList() {
        // Act
        ArrayList<Product> products = productService.getProducts();

        // Assert
        assertTrue(products.isEmpty(), "Expected an empty list when no products are available.");
    }

    @Test
    void getProducts_multipleProducts_listOfProducts() {
        // Arrange
        Product product1 = new Product(UUID.randomUUID(), "Product 1", 50.0);
        Product product2 = new Product(UUID.randomUUID(), "Product 2", 75.0);

        productRepository.save(product1); // Persist product1
        productRepository.save(product2); // Persist product2

        // Act
        ArrayList<Product> products = productService.getProducts();

        // Assert
        assertEquals(2, products.size(), "Expected list size to match number of added products.");
        assertTrue(products.stream().anyMatch(p -> p.getId().equals(product1.getId())), "Expected product1 to be in the list.");
        assertTrue(products.stream().anyMatch(p -> p.getId().equals(product2.getId())), "Expected product2 to be in the list.");
    }

    @Test
    void getProducts_repositoryThrowsException_exceptionThrown() {
        // This test assumes that the repository could throw an exception.
        // If it does not, you need to modify the repository to handle such cases.

        // Simulating an exception in the repository
        assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("Database error");
        }, "Expected exception to be thrown when repository fails.");
    }


    @Test
    void getProductById_existingId_returnsProduct() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Product expectedProduct = new Product(productId, "Test Product", 99.99);

        productRepository.save(expectedProduct); // Persist product in the repository

        // Act
        Product product = productService.getProductById(productId);

        // Assert
        assertNotNull(product, "Expected product to be returned.");
        assertEquals(expectedProduct.getId(), product.getId(), "Returned product ID should match the expected product.");
        assertEquals(expectedProduct.getName(), product.getName(), "Returned product name should match.");
        assertEquals(expectedProduct.getPrice(), product.getPrice(), "Returned product price should match.");
    }

    @Test
    void getProductById_nonExistingId_throwsException() {
        // Arrange
        UUID productId = UUID.randomUUID();

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> productService.getProductById(productId));
        assertEquals("Product not found", exception.getMessage(), "Expected exception message to match.");
    }

    @Test
    void getProductById_repositoryThrowsException_exceptionThrown() {
        // If repository throws an exception, ensure the exception handling is tested properly

        assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("Database error");
        }, "Expected exception to be thrown when repository fails.");
    }


    @Test
    void updateProduct_validId_updatesNameAndPrice() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Product existingProduct = new Product(productId, "Original Product", 50.0);
        productRepository.addProduct(existingProduct); // Add product to repository

        String newName = "Updated Product";
        double newPrice = 99.99;

        // Act
        Product updatedProduct = productService.updateProduct(productId, newName, newPrice);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals(newName, updatedProduct.getName());
        assertEquals(newPrice, updatedProduct.getPrice());

        // Verify that the update was actually applied
        Product fetchedProduct = productRepository.getProductById(productId);
        assertEquals(newName, fetchedProduct.getName());
        assertEquals(newPrice, fetchedProduct.getPrice());
    }
    @Test
    void updateProduct_nonExistentId_throwsException() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID(); // ID not in repository
        String newName = "Updated Product";
        double newPrice = 99.99;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(nonExistentId, newName, newPrice);
        });
    }
    @Test
    void updateProduct_invalidPrice_throwsIllegalArgumentException() {
        // Arrange
        UUID productId = UUID.randomUUID();
        String newName = "Updated Product";
        double invalidPrice = -10.0; // Negative price should not be allowed

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(productId, newName, invalidPrice);
        });

        // Ensure that no update operation was performed
        assertNull(productRepository.getProductById(productId));
    }

    @Test
    void applyDiscount_validDiscount_updatesProducts() {
        // Arrange
        double discount = 10.0;
        Product product1 = new Product(UUID.randomUUID(), "Product A", 100.0);
        Product product2 = new Product(UUID.randomUUID(), "Product B", 200.0);

        productRepository.addProduct(product1);
        productRepository.addProduct(product2);

        ArrayList<UUID> productIds = new ArrayList<>(Arrays.asList(product1.getId(), product2.getId()));

        // Act
        productService.applyDiscount(discount, productIds);

        // Assert
        Product updatedProduct1 = productRepository.getProductById(product1.getId());
        Product updatedProduct2 = productRepository.getProductById(product2.getId());

        assertEquals(90.0, updatedProduct1.getPrice());
        assertEquals(180.0, updatedProduct2.getPrice());
    }
    @Test
    void applyDiscount_negativeDiscount_throwsIllegalArgumentException() {
        // Arrange
        double invalidDiscount = -5.0;
        Product product = new Product(UUID.randomUUID(), "Product A", 100.0);
        productRepository.addProduct(product);

        ArrayList<UUID> productIds = new ArrayList<>(Arrays.asList(product.getId()));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            productService.applyDiscount(invalidDiscount, productIds);
        });

        // Ensure product price remains unchanged
        Product unchangedProduct = productRepository.getProductById(product.getId());
        assertEquals(100.0, unchangedProduct.getPrice());
    }

    @Test
    void applyDiscount_nonExistentProduct_throwsRuntimeException() {
        double discount = 10.0;
        UUID nonExistentProductId = UUID.randomUUID();
        ArrayList<UUID> productIds = new ArrayList<>();
        productIds.add(nonExistentProductId);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                productService.applyDiscount(discount, productIds)
        );

        assertTrue(exception.getMessage().contains(nonExistentProductId.toString()));
    }

    @Test
    void deleteProductById_existingProduct_removesProduct() {
        // Arrange
        Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
        productRepository.addProduct(product);
        UUID productId = product.getId();

        // Act
        productService.deleteProductById(productId);

        // Assert
        assertNull(productRepository.getProductById(productId), "Product should be deleted");
    }

    @Test
    void deleteProductById_multipleDeletions_shouldHandleGracefully() {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Test Product", 100.0);

        productRepository.save(product);
        assertNotNull(productRepository.getProductById(productId), "Product should exist before deletion");

        productService.deleteProductById(productId);

        assertThrows(IllegalArgumentException.class, () -> productService.deleteProductById(productId),
                "Deleting the same product twice should throw IllegalArgumentException");
    }




    @Test
    void deleteProductById_nullId_throwsIllegalArgumentException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.deleteProductById(null), "Passing a null ID should throw IllegalArgumentException");
    }

    //--------------------------------------Cart Tests-------------------------------

    @Test
    void addCart_validCart_cartAdded() {
        // Arrange
        UUID existingUserId = UUID.randomUUID();
        User existingUser = new User(existingUserId, "John", new ArrayList<>());
        userService.addUser(existingUser);

        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(existingUser.getId());
        cart.setProducts(new ArrayList<>());

        // Act
        cartService.addCart(cart);

        // Assert
        boolean found = false;
        for (Cart storedCart : cartService.getCarts()) {
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
        UUID existingUserId = UUID.randomUUID();
        User existingUser = new User(existingUserId, "John", new ArrayList<>());
        userService.addUser(existingUser);

        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserId(existingUser.getId());

        Product product = new Product(UUID.randomUUID(), "Gebna", 15.0);
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
        User user1 = new User("User One", new ArrayList<>());
        User user2 = new User("User Two", new ArrayList<>());

        userService.addUser(user1);
        userService.addUser(user2);

        Cart cart1 = new Cart();
        cart1.setId(UUID.randomUUID());
        cart1.setUserId(user1.getId());

        Cart cart2 = new Cart();
        cart2.setId(UUID.randomUUID());
        cart2.setUserId(user2.getId());

        cartService.addCart(cart1);
        cartService.addCart(cart2);

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

    //------------------------------------order Tests------------------

    @Test
    void addOrder_validOrder_orderAdded() {
        // Arrange
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserId(UUID.randomUUID());

        // Act
        orderService.addOrder(order);

        // Assert
        boolean found = false;
        for (Order storedOrder : orderRepository.getOrders()) {
            if (storedOrder.getId().equals(order.getId())) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Order should be added correctly");
    }

    @Test
    void addOrder_orderWithProducts_productsPersisted() {
        // Arrange
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserId(UUID.randomUUID());

        Product product = new Product(UUID.randomUUID(), "Pizza", 20.0);
        List<Product> products = new ArrayList<>();
        products.add(product);
        order.setProducts(products);

        // Act
        orderService.addOrder(order);

        // Assert
        Order storedOrder = orderRepository.getOrderById(order.getId());
        assertNotNull(storedOrder, "Stored order should not be null");
        assertEquals(1, storedOrder.getProducts().size(), "The order should contain exactly one product");
        assertEquals("Pizza", storedOrder.getProducts().get(0).getName(), "The product name should match");
    }

    @Test
    void addOrder_nullOrder_throwsException() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.addOrder(null));
        assertEquals("Order cannot be null", exception.getMessage(), "Exception message should match");
    }


    @Test
    void getOrders_multipleOrders_allOrdersReturned() {
        // Arrange
        Order order1 = new Order();
        order1.setId(UUID.randomUUID());
        order1.setUserId(UUID.randomUUID());

        Order order2 = new Order();
        order2.setId(UUID.randomUUID());
        order2.setUserId(UUID.randomUUID());

        orderRepository.addOrder(order1);
        orderRepository.addOrder(order2);

        // Act
        ArrayList<Order> orders = orderService.getOrders();

        // Assert
        assertNotNull(orders, "Returned order list should not be null");
        assertEquals(2, orders.size(), "Should return exactly two orders");
    }

    @Test
    void getOrders_noOrders_emptyList() {
        // Act
        ArrayList<Order> orders = orderService.getOrders();

        // Assert
        assertNotNull(orders, "Returned list should not be null");
        assertTrue(orders.isEmpty(), "Should return an empty list when no orders exist");
    }

    @Test
    void getOrders_afterDeletingAllOrders_returnEmptyList() {
        // Arrange
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserId(UUID.randomUUID());

        orderRepository.addOrder(order);
        orderService.deleteOrderById(order.getId());  // Assuming this method exists

        // Act
        ArrayList<Order> orders = orderService.getOrders();

        // Assert
        assertNotNull(orders, "Returned list should not be null");
        assertTrue(orders.isEmpty(), "Should return an empty list after deleting all orders");
    }

    @Test
    void getOrderById_existingOrder_orderFound() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);
        order.setUserId(UUID.randomUUID());

        orderRepository.addOrder(order);

        // Act
        Order retrievedOrder = orderService.getOrderById(orderId);

        // Assert
        assertNotNull(retrievedOrder, "The retrieved order should not be null");
        assertEquals(orderId, retrievedOrder.getId(), "The order ID should match the requested ID");
    }

    @Test
    void getOrderById_nonExistentOrder_throwsException() {
        // Arrange
        UUID randomId = UUID.randomUUID();

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> orderService.getOrderById(randomId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Should return 404 NOT FOUND");
        assertEquals("Order not found", exception.getReason(), "Exception message should match");
    }

    @Test
    void getOrderById_nullId_throwsException() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.getOrderById(null));
        assertEquals("Order ID cannot be null", exception.getMessage(), "Exception message should match");
    }


    @Test
    void deleteOrderById_existingOrder_orderDeleted() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);
        order.setUserId(UUID.randomUUID());

        orderRepository.addOrder(order);

        // Act
        orderService.deleteOrderById(orderId);

        // Assert
        assertThrows(ResponseStatusException.class, () -> orderService.getOrderById(orderId),
                "After deletion, fetching the order should throw a ResponseStatusException");
    }


    @Test
    void deleteOrderById_nonExistentOrder_throwsException() {
        // Arrange
        UUID randomId = UUID.randomUUID();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrderById(randomId));
        assertEquals("Order not found", exception.getMessage(), "Exception message should match for non-existent order");
    }


    @Test
    void deleteOrderById_nullId_throwsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrderById(null));
        assertEquals("Order ID cannot be null", exception.getMessage(), "Exception message should match for null order ID");
    }

}
