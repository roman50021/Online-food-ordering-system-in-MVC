package com.example.onlinelearningplatform.service.implementations;

import com.example.onlinelearningplatform.exception.NullEntityReferenceException;
import com.example.onlinelearningplatform.models.Dish;
import com.example.onlinelearningplatform.models.Menu;
import com.example.onlinelearningplatform.repos.DishRepository;
import com.example.onlinelearningplatform.service.services.DishService;
import com.example.onlinelearningplatform.service.services.MenuService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final MenuService menuService;
    @Override
    public Dish create(Dish dish) {
        if (dish != null) {
            if (dish.getMenu() != null) { // проверяем, что у блюда есть меню
                // Сохраняем меню, если его нет в базе
                Menu menu = menuService.readById(dish.getMenu().getId());
                dish.setMenu(menu);
            } else {
                throw new NullEntityReferenceException("Menu cannot be null for Dish");
            }
            return dishRepository.save(dish);
        }
        throw new NullEntityReferenceException("Dish cannot be 'null'");
    }
    @Override
    public Dish readById(long id) {
        return dishRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Dish with id " + id + " not found"));
    }
    @Override
    public Dish update(Dish dish) {
        if (dish != null) {
            if (dish.getMenu() != null) { // проверяем, что у блюда есть меню
                // Сохраняем меню, если его нет в базе
                Menu menu = menuService.readById(dish.getMenu().getId());
                dish.setMenu(menu);
            } else {
                throw new NullEntityReferenceException("Menu cannot be null for Dish");
            }
            return dishRepository.save(dish);
        }
        throw new NullEntityReferenceException("Dish cannot be 'null'");
    }
    @Override
    public void delete(long id) {
        dishRepository.delete(readById(id));
    }

    @Override
    public List<Dish> getAll() {
        List<Dish> dishes = dishRepository.findAll();
        return dishes.isEmpty() ? new ArrayList<>() : dishes;
    }
}
