package com.example.onlinelearningplatform.service.services;

import com.example.onlinelearningplatform.dto.menu.MenuDto;
import com.example.onlinelearningplatform.models.Menu;

import java.util.List;

public interface MenuService {
    Menu create(Menu menu);
    Menu readById(long id);
    Menu update(Menu menu);
    void delete(long id);

    List<Menu> getAll();
}
