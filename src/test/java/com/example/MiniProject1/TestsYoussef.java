package com.example.MiniProject1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
public class TestsYoussef {

    @Value("${spring.application.orderDataPath}")
    private String orderDataPath;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    private UUID userId;
    private UUID orderId;
    private Order testOrder;

    public void overrideAll() {
        try {
            objectMapper.writeValue(new File(orderDataPath), new ArrayList<Order>());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

    public Order addOrder(Order order) {
        try {
            File file = new File(orderDataPath);
            ArrayList<Order> orders;
            if (!file.exists()) {
                orders = new ArrayList<>();
            } else {
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
            return new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        orderId = UUID.randomUUID();
        testOrder = new Order();
        testOrder.setId(orderId);
        testOrder.setUserId(userId);
        testOrder.setProducts(new ArrayList<>());
        overrideAll();
    }

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
        assertThrows(IllegalArgumentException.class, () -> orderService.getOrderById(orderId),
                "After deletion, fetching the order should throw an exception");
    }

    @Test
    void deleteOrderById_nonExistentOrder_throwsException() {
        // Arrange
        UUID randomId = UUID.randomUUID();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrderById(randomId));
        assertEquals("Order not found", exception.getMessage(), "Exception message should match");
    }

    @Test
    void deleteOrderById_nullId_throwsException() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrderById(null));
        assertEquals("Order not found", exception.getMessage(), "Exception message should match");
    }






}
