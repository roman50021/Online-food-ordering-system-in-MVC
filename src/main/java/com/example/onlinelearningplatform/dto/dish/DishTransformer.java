package com.example.onlinelearningplatform.dto.dish;

import com.example.onlinelearningplatform.models.Dish;
import com.example.onlinelearningplatform.models.Menu;
import com.example.onlinelearningplatform.service.services.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishTransformer {
    private final MenuService menuService;

    public DishDto toDto(Dish dish) {
        return DishDto.builder()
                .id(dish.getId())
                .name(dish.getName())
                .description(dish.getDescription())
                .servingWeight(dish.getServingWeight())
                .price(dish.getPrice())
                .menuId(dish.getMenu().getId())
                .build();
    }

    public Dish toEntity(DishDto dishDto) {
        Dish dish = Dish.builder()
                .id(dishDto.getId())
                .name(dishDto.getName())
                .description(dishDto.getDescription())
                .servingWeight(dishDto.getServingWeight())
                .price(dishDto.getPrice())
                .build();

        // Загрузить объект Menu из базы данных по его id
        Menu menu = menuService.readById(dishDto.getMenuId());

        // Установить загруженный объект Menu для объекта Dish
        dish.setMenu(menu);

        return dish;
    }
}
