package com.example.repository;

import com.example.model.User;
import com.example.model.Order;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@SuppressWarnings("rawtypes")
public class UserRepository extends MainRepository<User> {

//    private static final String DATA_PATH = System.getenv("USER_DATA_PATH");;
private static final String DATA_PATH = "src/main/java/com/example/data/users.json";
    private final User user;

    @Override
    protected String getDataPath() {
        return DATA_PATH;
    }

    @Override
    protected Class<User[]> getArrayType() {
        return User[].class;
    }

    public UserRepository(User user) {
        this.user = user;
    }

    public ArrayList<User> getUsers() {
        return findAll();
    }

    public User getUserById(UUID userId) {
        return findAll().stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public User addUser(User user) {
        save(user);
        return user;
    }
    public List<Order> getOrdersByUserId(UUID userId) {
        User user = getUserById(userId);
        return user.getOrders();
    }

    public void addOrderToUser(UUID userId, Order order) {
        User user = getUserById(userId);

        // Create a new list and add existing + new orders
        List<Order> updatedOrders = new ArrayList<>(user.getOrders());
        updatedOrders.add(order);

        // Set the updated list back to the user
        user.setOrders(updatedOrders);

        delete(userId);
        save(user);
    }


    public void removeOrderFromUser(UUID userId, UUID orderId) {
        User user = getUserById(userId);
        user.setOrders(user.getOrders().stream()
                .filter(order -> !order.getId().equals(orderId))
                .collect(Collectors.toList()));
        delete(userId);
        save(user);
    }

    public void deleteUserById(UUID userId) {
        delete(userId);
    }
}
