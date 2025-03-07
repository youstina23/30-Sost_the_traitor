package com.example.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.exception.BadRequestException;
import com.example.model.Cart;
import com.example.model.Model;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

@Primary
@Repository
public abstract class MainRepository<T extends Model> {

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected abstract String getDataPath();
    protected abstract Class<T[]> getArrayType();

    public MainRepository(){

    }

    public T findById(UUID id) {
        try {
            ArrayList<T> entities = findAll();
            for (T entity : entities) {
                UUID entityId = (UUID) entity.getClass().getMethod("getId").invoke(entity);
                if (entityId.equals(id)) {
                    return entity;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error finding entity by ID: " + id, e);
        }
        return null; // or throw an exception if preferred
    }

    public ArrayList<T> findAll() {
        try {
            File file = new File(getDataPath());
            if (!file.exists()) {
                return new ArrayList<>();
            }
            T[] array = objectMapper.readValue(file, getArrayType()); // Deserialize to array first
            return new ArrayList<>(Arrays.asList(array));
            // return objectMapper.readValue(file, new TypeReference<ArrayList<T>>(){});
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }

    public void saveAll(ArrayList<T> data) {
        try {
            objectMapper.writeValue(new File(getDataPath()), data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

    public void save(T data) {
        try {
            ArrayList<T> allData = findAll();
            UUID id = (UUID) data.getClass().getMethod("getId").invoke(data);
            T prev = findById(id);
            if (prev != null) {
                throw new BadRequestException("ID is duplicated");
            }
            allData.add(data);
            saveAll(allData);
        } catch(Exception e) {
            throw new BadRequestException("Unable to save data");
        }
    }



    public void overrideData(ArrayList<T> data) {
        saveAll(data);
    }

    public void delete(UUID id) {
        ArrayList<T> ts = findAll();
        int initialLength = ts.size();

        ts.removeIf(t -> t.getId().equals(id));

        int finalLength = ts.size();

        if(initialLength == finalLength) {
            throw new BadRequestException("User not found");
        }
        saveAll(ts);
    }




}