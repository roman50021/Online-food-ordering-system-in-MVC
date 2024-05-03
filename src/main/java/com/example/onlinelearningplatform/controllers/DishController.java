package com.example.onlinelearningplatform.controllers;

import com.example.onlinelearningplatform.dto.dish.DishDto;
import com.example.onlinelearningplatform.dto.dish.DishTransformer;
import com.example.onlinelearningplatform.models.Dish;
import com.example.onlinelearningplatform.service.services.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dish")
public class DishController {
    private final DishService dishService;
    private final DishTransformer dishTransformer;

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String create(Model model){
        model.addAttribute("dish", new DishDto());
        return "create-dish";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createDish(@Valid @ModelAttribute("dishDto") DishDto dishDto, BindingResult result) {
        if (result.hasErrors()) {
            return "create-dish";
        }
        Dish dish = dishTransformer.toEntity(dishDto);
        dishService.create(dish);
        return "redirect:/dish/list";
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getDishList(Model model) {
        List<Dish> dishes = dishService.getAll();
        List<DishDto> dishDtos = dishes.stream()
                .map(dishTransformer::toDto)
                .collect(Collectors.toList());
        model.addAttribute("dishes", dishDtos);
        return "dish-list";
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getDishById(@PathVariable("id") long id, Model model) {
        Dish dish = dishService.readById(id);
        DishDto dishDto = dishTransformer.toDto(dish);
        model.addAttribute("dish", dishDto);
        return "dish-details";
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateDishForm(@PathVariable("id") long id, Model model) {
        Dish dish = dishService.readById(id);
        DishDto dishDto = dishTransformer.toDto(dish);
        model.addAttribute("dish", dishDto);
        return "update-dish";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateDish(@PathVariable("id") long id, @Valid @ModelAttribute("dishDto") DishDto dishDto, BindingResult result) {
        if (result.hasErrors()) {
            return "update-dish";
        }
        Dish dish = dishTransformer.toEntity(dishDto);
        dish.setId(id);
        dishService.update(dish);
        return "redirect:/dish/list";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteDish(@PathVariable("id") long id) {
        dishService.delete(id);
        return "redirect:/dish/list";
    }


}
