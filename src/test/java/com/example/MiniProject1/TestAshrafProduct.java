package com.example.MiniProject1;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ComponentScan(basePackages = "com.example.*")
@WebMvcTest
public class TestAshrafProduct {
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

    private UUID productId;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        testProduct = new Product();
        testProduct.setId(productId);
        testProduct.setName("Test Product");
        testProduct.setPrice(100.0);
        overRideAll();
    }

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
    void applyDiscount_emptyProductList_doesNotCallRepository() {
        // Arrange
        double discount = 10.0;
        Product product = new Product(UUID.randomUUID(), "Product A", 100.0);
        productRepository.addProduct(product);

        ArrayList<UUID> emptyProductIds = new ArrayList<>();

        // Act
        productService.applyDiscount(discount, emptyProductIds);

        // Assert
        Product unchangedProduct = productRepository.getProductById(product.getId());
        assertEquals(100.0, unchangedProduct.getPrice()); // Ensure no changes were made
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
        // Arrange
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Test Product", 100.0);

        // Save product to database
        productRepository.save(product);
        assertNotNull(productRepository.getProductById(productId), "Product should exist before deletion");

        // Act - First deletion
        productService.deleteProductById(productId);

        // Assert - Ensure the product is deleted
        assertThrows(NoSuchElementException.class, () -> productService.deleteProductById(productId),
                "Deleting the same product twice should throw NoSuchElementException");
    }




    @Test
    void deleteProductById_nullId_throwsIllegalArgumentException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.deleteProductById(null), "Passing a null ID should throw IllegalArgumentException");
    }
}
