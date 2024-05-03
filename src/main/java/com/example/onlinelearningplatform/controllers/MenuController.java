package com.example.onlinelearningplatform.controllers;

import com.example.onlinelearningplatform.dto.menu.MenuDto;
import com.example.onlinelearningplatform.dto.menu.MenuTransformer;
import com.example.onlinelearningplatform.models.Menu;
import com.example.onlinelearningplatform.service.services.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;
    private final MenuTransformer menuTransformer;

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String create(Model model){
        model.addAttribute("menu", new MenuDto());
        return "create-menu";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createMenu(@Valid @ModelAttribute("menuDto") MenuDto menuDto, BindingResult result) {
        if (result.hasErrors()) {
            return "create-menu";
        }
        Menu menu = menuTransformer.toEntity(menuDto);
        menuService.create(menu);
        return "redirect:/menu/list";
    }

    @GetMapping("/lista")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getMenuList(Model model) {
        List<Menu> menus = menuService.getAll();
        List<MenuDto> menuDtos = menus.stream()
                .map(menuTransformer::toDto)
                .collect(Collectors.toList());
        model.addAttribute("menus", menuDtos);
        return "menu-list";
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getMenuListForUser(Model model) {
        List<Menu> menus = menuService.getAll();
        List<MenuDto> menuDtos = menus.stream()
                .map(menuTransformer::toDto)
                .collect(Collectors.toList());
        model.addAttribute("menus", menuDtos);
        return "menu-userlist";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getMenuById(@PathVariable("id") long id, Model model) {
        Menu menu = menuService.readById(id);
        MenuDto menuDto = menuTransformer.toDto(menu);
        model.addAttribute("menu", menuDto);
        return "menu-details";
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateMenuForm(@PathVariable("id") long id, Model model) {
        Menu menu = menuService.readById(id);
        MenuDto menuDto = menuTransformer.toDto(menu);
        model.addAttribute("menuDto", menuDto);
        return "update-menu";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateMenu(@PathVariable("id") long id, @Valid @ModelAttribute("menuDto") MenuDto menuDto, BindingResult result) {
        if (result.hasErrors()) {
            return "update-menu";
        }
        Menu menu = menuTransformer.toEntity(menuDto);
        menu.setId(id);
        menuService.update(menu);
        return "redirect:/menu/list";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteMenu(@PathVariable("id") long id) {
        menuService.delete(id);
        return "redirect:/menu/list";
    }


}
