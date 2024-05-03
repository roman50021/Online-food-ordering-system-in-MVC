package com.example.onlinelearningplatform.dto.menu;

import com.example.onlinelearningplatform.models.Menu;
import org.springframework.stereotype.Component;

@Component
public class MenuTransformer {
    public MenuDto toDto(Menu menu) {
        return MenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .servingWeight(menu.getServingWeight())
                .price(menu.getPrice())
                .build();
    }

    public Menu toEntity(MenuDto menuDto) {
        return Menu.builder()
                .id(menuDto.getId())
                .name(menuDto.getName())
                .description(menuDto.getDescription())
                .servingWeight(menuDto.getServingWeight())
                .price(menuDto.getPrice())
                .build();
    }
}
