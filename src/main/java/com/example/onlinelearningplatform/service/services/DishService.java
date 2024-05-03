package com.example.onlinelearningplatform.service.services;

import com.example.onlinelearningplatform.models.Dish;

import java.util.List;

public interface DishService {
    Dish create(Dish dish);
    Dish readById(long id);
    Dish update(Dish dish);
    void delete(long id);

    List<Dish> getAll();
}
