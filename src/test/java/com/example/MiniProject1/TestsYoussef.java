package com.example.MiniProject1;

import com.example.controller.OrderController;
import com.example.model.Order;
import com.example.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TestsYoussef {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private UUID orderId;
    private UUID userId;
    private Order order;

    private List<Order> orders;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mock objects
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build(); // Sets up MockMvc

        orders = new ArrayList<>();
        orders.add(new Order(UUID.randomUUID(), 100.0, new ArrayList<>()));
        orders.add(new Order(UUID.randomUUID(), 200.0, new ArrayList<>()));
    }


    @Test
    void testAddOrder_Success() throws Exception {
        doNothing().when(orderService).addOrder(any(Order.class)); // Mocking the addOrder method

        mockMvc.perform(post("/order/")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(order))) // Convert order object to JSON
                .andExpect(status().isOk()); // Expect 200 OK status
    }

    // 🔴 Test: Add Order - Bad Request (POST /order/) (Invalid request body)
    @Test
    void testAddOrder_BadRequest() throws Exception {
        Order invalidOrder = new Order(null, 0.0, new ArrayList<>()); // Invalid order (null userId, zero total price)

        mockMvc.perform(post("/order/")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(invalidOrder))) // Invalid request body
                .andExpect(status().isBadRequest()) // Expect 400 Bad Request
                .andExpect(content().string("Invalid order data"));
    }

    // 🔴 Test: Add Order - Service Layer Error (POST /order/) (Exception thrown)
    @Test
    void testAddOrder_ServiceError() throws Exception {
        doThrow(new RuntimeException("Order service failed")).when(orderService).addOrder(any(Order.class));

        mockMvc.perform(post("/order/")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isInternalServerError()) // Expect 500 Internal Server Error
                .andExpect(content().string("Order service failed"));
    }


    @Test
    void testGetOrderById_Success() throws Exception {
        when(orderService.getOrderById(orderId)).thenReturn(order); // Mock the orderService response

        mockMvc.perform(get("/order/{orderId}", orderId))
                .andExpect(status().isOk()) // Expect 200 OK status
                .andExpect(content().json(new ObjectMapper().writeValueAsString(order))); // Expect the order in JSON format
    }

    // 🔴 Test: Get Order by ID - Not Found (GET /order/{orderId})
    @Test
    void testGetOrderById_NotFound() throws Exception {
        when(orderService.getOrderById(orderId)).thenThrow(new RuntimeException("Order not found")); // Mock throwing exception

        mockMvc.perform(get("/order/{orderId}", orderId))
                .andExpect(status().isNotFound()) // Expect 404 Not Found status
                .andExpect(content().string("Order not found"));
    }

    // 🔴 Test: Get Order by ID - Service Layer Error (GET /order/{orderId})
    @Test
    void testGetOrderById_ServiceError() throws Exception {
        when(orderService.getOrderById(orderId)).thenThrow(new RuntimeException("Service error")); // Mock a service error

        mockMvc.perform(get("/order/{orderId}", orderId))
                .andExpect(status().isInternalServerError()) // Expect 500 Internal Server Error
                .andExpect(content().string("Service error"));
    }


    @Test
    void testGetOrders_Success() throws Exception {
        when(orderService.getOrders()).thenReturn(new ArrayList<>(orders));

        mockMvc.perform(get("/order/"))
                .andExpect(status().isOk()) // Expect 200 OK status
                .andExpect(content().json(new ObjectMapper().writeValueAsString(orders))); // Expect the orders in JSON format
    }

    // 🔴 Test: Get All Orders - No Orders Found (GET /order/)
    @Test
    void testGetOrders_EmptyList() throws Exception {
        when(orderService.getOrders()).thenReturn(new ArrayList<>()); // Mock an empty list of orders

        mockMvc.perform(get("/order/"))
                .andExpect(status().isOk()) // Expect 200 OK status
                .andExpect(content().json("[]")); // Expect an empty list
    }

    // 🔴 Test: Get All Orders - Service Error (GET /order/)
    @Test
    void testGetOrders_ServiceError() throws Exception {
        when(orderService.getOrders()).thenThrow(new RuntimeException("Service error")); // Mock a service error

        mockMvc.perform(get("/order/"))
                .andExpect(status().isInternalServerError()) // Expect 500 Internal Server Error status
                .andExpect(content().string("Service error"));
    }


    @Test
    void testDeleteOrder_Success() throws Exception {
        // Mocking the service call to delete the order
        doNothing().when(orderService).deleteOrderById(orderId);  // Use doNothing() for void method

        mockMvc.perform(delete("/order/delete/{orderId}", orderId))
                .andExpect(status().isOk()) // Expect 200 OK status
                .andExpect(content().string("Order deleted successfully")); // Expect the deletion success message
    }


    // 🔴 Test: Delete Order - Order Not Found (DELETE /order/delete/{orderId})
    @Test
    void testDeleteOrder_OrderNotFound() throws Exception {
        // Simulating the case where the order does not exist
        doThrow(new IllegalArgumentException("Order not found")).when(orderService).deleteOrderById(orderId);

        mockMvc.perform(delete("/order/delete/{orderId}", orderId))
                .andExpect(status().isOk()) // Expect 200 OK status
                .andExpect(content().string("Order not found")); // Expect the order not found message
    }

    // 🔴 Test: Delete Order - Service Error (DELETE /order/delete/{orderId})
    @Test
    void testDeleteOrder_ServiceError() throws Exception {
        // Simulating an unexpected error in the service layer
        doThrow(new RuntimeException("Unexpected error")).when(orderService).deleteOrderById(orderId);

        mockMvc.perform(delete("/order/delete/{orderId}", orderId))
                .andExpect(status().isInternalServerError()) // Expect 500 Internal Server Error status
                .andExpect(content().string("Unexpected error")); // Expect the error message
    }


}
