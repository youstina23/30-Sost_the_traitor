package com.example.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Objects;

@Component
public class User implements Model {

    private UUID id = UUID.randomUUID();
    private String name;
    private List<Order> orders = new ArrayList<>();

    public User() {
    }

    public User(UUID id, String name, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public User(String name, List<Order> orders) {
        this.id =UUID.randomUUID();
        this.name = name;
        this.orders = orders;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
