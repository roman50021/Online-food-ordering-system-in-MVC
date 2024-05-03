package com.example.onlinelearningplatform.service.implementations;

import com.example.onlinelearningplatform.exception.NullEntityReferenceException;
import com.example.onlinelearningplatform.models.Dish;
import com.example.onlinelearningplatform.models.Menu;
import com.example.onlinelearningplatform.repos.MenuRepository;
import com.example.onlinelearningplatform.service.services.MenuService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    @Override
    public Menu create(Menu menu) {
        if (menu != null){
            return menuRepository.save(menu);
        }
        throw new NullEntityReferenceException("Menu cannot be null");
    }

    @Override
    public Menu update(Menu menu) {
        if (menu != null){
            readById(menu.getId());
            return menuRepository.save(menu);
        }
        throw new NullEntityReferenceException("Menu cannot bew null");
    }

    @Override
    public Menu readById(long id) {
        return menuRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Menu with id " + id + " not found")
        );
    }
    @Override
    public void delete(long id) {
        menuRepository.delete(readById(id));
    }

    @Override
    public List<Menu> getAll() {
        List<Menu> menus = menuRepository.findAll();
        return menus.isEmpty() ? new ArrayList<>() : menus;
    }
    @Override
    public List<Dish> getDishesByMenuId(long menuId) {
        Optional<Menu> menuOptional = menuRepository.findById(menuId);
        if (menuOptional.isPresent()) {
            Menu menu = menuOptional.get();
            return menu.getDishes(); // Предполагается, что у класса Menu есть метод getDishes() для получения списка блюд
        } else {
            // Меню с указанным ID не найдено
            throw new RuntimeException("Menu not found with id: " + menuId);
        }
    }
}
