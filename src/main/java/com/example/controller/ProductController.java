package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

import com.example.model.Product;
import com.example.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping("/")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(@PathVariable UUID id, @RequestBody Map<String,Object> body) {
        return productService.updateProduct(id, (String)body.get("newName"), (double)body.get("newPrice"));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProductById(@PathVariable UUID id) {
        try{

            productService.deleteProductById(id);
            return "Product deleted successfully";
        }catch(IllegalArgumentException e){
            return "Product not found";
        }
    }



    

    
}
