package com.example.model;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class Product {

    private UUID id;
    private String name;
    private double price;

    public Product(){
        
    }
    public Product(UUID id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String toString(){
        return "Product ID: " + id + " Name: " + name + " Price: " + price;
    }
    
    
}
