package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

import com.example.model.Product;
import com.example.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public Product addProduct(Product product) {
        return productRepository.addProduct(product);
    }
    public List<Product> getProducts() {
        return productRepository.getProducts();
    }
    
    public Product getProductById(UUID id) {
        return productRepository.getProductById(id);
    }

    public Product updateProduct(UUID id, String newName,double newPrice) {
        return productRepository.updateProduct(id, newName, newPrice);
    }
    public void deleteProductById(UUID id) throws IllegalArgumentException {
        productRepository.deleteProductById(id);
    }
    
}
