package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class ProductService extends MainService<Product> {

    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        if (product == null) {
            throw new NullPointerException("Product cannot be null");
        }
        return productRepository.addProduct(product);
    }


    public ArrayList<Product> getProducts() {
        return productRepository.getProducts();
    }

    public Product getProductById(UUID productId) {
        Product product = productRepository.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        return product;
    }


    public Product updateProduct(UUID productId, String newName, double newPrice) {
        // Validate price
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        // Validate product existence
        Product existingProduct = productRepository.getProductById(productId);
        if (existingProduct == null) {
            throw new RuntimeException("Product not found");
        }

        // Proceed with update
        return productRepository.updateProduct(productId, newName, newPrice);
    }


    public void applyDiscount(double discount, ArrayList<UUID> productIds) {
        if (discount < 0) {
            throw new IllegalArgumentException("Discount cannot be negative");
        }
        productRepository.applyDiscount(discount, productIds);
    }


    public void deleteProductById(UUID productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }

        Product product = productRepository.getProductById(productId);
        if (product == null) {
            throw new NoSuchElementException("Product with ID " + productId + " does not exist.");
        }

        productRepository.deleteProductById(productId);

        // Ensure the product is actually deleted
        if (productRepository.getProductById(productId) != null) {
            throw new NoSuchElementException("Product with ID " + productId + " still exists after deletion.");
        }
    }

}
