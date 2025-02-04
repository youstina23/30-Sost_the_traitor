package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.model.Product;

@Component
public class ProductRepository {

    public static List<Product> products = new ArrayList<>();

    public ProductRepository() {
        
    }

    public Product addProduct(Product product) {
        products.add(product);
        return product;
    }
    public List<Product> getProducts() {
        return products;
    }
    public Product getProductById(UUID id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
    public Product updateProduct(UUID id, String newName,double newPrice) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                p.setName(newName);
                p.setPrice(newPrice);
                return p;
            }
        }
        return null;
    }
    public void deleteProductById(UUID id) throws IllegalArgumentException {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                products.remove(product);
                return;
            }
        }
        throw new IllegalArgumentException("Product not found");
    }

    
    
}
