package com.example.onlinelearningplatform.dto.menu;

import com.example.onlinelearningplatform.dto.dish.DishDto;
import com.example.onlinelearningplatform.models.Dish;
import com.example.onlinelearningplatform.models.Menu;
import org.springframework.stereotype.Component;

@Component
public class MenuTransformer {
    public MenuDto toDto(Menu menu) {
        return MenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .build();
    }

    public Menu toEntity(MenuDto menuDto) {
        return Menu.builder()
                .id(menuDto.getId())
                .name(menuDto.getName())
                .build();
    }
}
