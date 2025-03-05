package com.example.service;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.example.repository.MainRepository;
import com.example.model.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Primary
@Service
public abstract class MainService<T extends Model> {
    protected final MainRepository<T> repository;

    public MainService(MainRepository<T> repository) {
        this.repository = repository;
    }

    // Generic method to add an entity
    public void add(T entity) {
        repository.save(entity);
    }

    // Generic method to retrieve all entities
    public ArrayList<T> getAll() {
        return repository.findAll();
    }

    // Generic method to get an entity by ID
    public T getById(UUID id) {
        return repository.findById(id);
    }

    // Generic method to delete an entity
    public void delete(UUID id) {
        repository.delete(id);
    }
}
