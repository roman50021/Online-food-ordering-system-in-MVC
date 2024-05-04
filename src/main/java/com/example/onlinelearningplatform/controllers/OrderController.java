package com.example.onlinelearningplatform.controllers;

import com.example.onlinelearningplatform.models.Dish;
import com.example.onlinelearningplatform.models.Order;
import com.example.onlinelearningplatform.models.User;
import com.example.onlinelearningplatform.service.services.DishService;
import com.example.onlinelearningplatform.service.services.OrderService;
import com.example.onlinelearningplatform.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import com.example.onlinelearningplatform.models.OrderStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final DishService dishService;

    @PostMapping("/create")
    public String createOrder(@RequestParam("address") String address, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null && !address.isEmpty()) {
            User user = userService.findByEmail(userDetails.getUsername());
            if (user != null) {
                Order order = Order.builder()
                        .user(user)
                        .address(address)
                        .createdAt(LocalDateTime.now())
                        .status(OrderStatus.PROCESSING)
                        .items(new ArrayList<>())
                        .paid(true)
                        .build();

                double totalPrice = calculateTotalPrice(order);
                order.setTotalPrice(totalPrice);

                orderService.create(order);
                order.setItems(null);
            }
        }
        model.addAttribute("order", new Order());
        return "redirect:/order";
    }

    private double calculateTotalPrice(Order order) {
        double totalPrice = 0.0;
        for (Dish dish : order.getItems()) {
            totalPrice += dish.getPrice();
        }
        return totalPrice;
    }

    @GetMapping
    public String getOrderPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("order", new Order());
        if (userDetails != null) {
            User user = userService.findByEmail(userDetails.getUsername());
            if (user != null) {
                List<Order> userOrders = orderService.getByUserId(user.getId());
                model.addAttribute("previousOrders", userOrders);
                // Передать все заказы пользователя, а не только текущий
                Order currentOrder = getCurrentOrderOrCreateNew(user);
                if (currentOrder != null && currentOrder.getItems() != null) {
                    model.addAttribute("currentOrder", currentOrder);
                }
            }
        }
        return "order-page";
    }

    @GetMapping("/old")
    public String getMyOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            User user = userService.findByEmail(userDetails.getUsername());
            if (user != null) {
                List<Order> userOrders = orderService.getByUserId(user.getId());
                model.addAttribute("userOrders", userOrders);
            }
        }
        return "my-orders-page"; // Это имя представления для страницы с предыдущими заказами
    }

    @GetMapping("/remove-item/{orderId}/{itemId}")
    public String removeItemFromOrder(@PathVariable("orderId") long orderId, @PathVariable("itemId") long itemId) {
        Order order = orderService.readById(orderId);
        order.getItems().removeIf(item -> item.getId() == itemId);
        orderService.update(order);
        return "redirect:/order";
    }

    @PostMapping("/add/{dishId}")
    public String addItemToOrder(@PathVariable("dishId") long dishId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            User user = userService.findByEmail(userDetails.getUsername());
            if (user != null) {
                Order currentOrder = getCurrentOrderOrCreateNew(user);
                Dish dish = dishService.readById(dishId);
                if (dish != null) {
                    currentOrder.getItems().add(dish);
                    orderService.update(currentOrder);
                    model.addAttribute("dishes", dishService.getAll()); // Обновляем список блюд в модели
                    model.addAttribute("currentOrder", currentOrder); // Передаем обновленный заказ на страницу
                    if (dish.getMenu() != null) {
                        return "redirect:/menu/dishes/" + dish.getMenu().getId();
                    }
                }
            }
        }
        return "redirect:/home";
    }

    private Order getCurrentOrderOrCreateNew(User user) {
        // Получаем текущий активный заказ пользователя (если есть)
        List<Order> currentOrders = orderService.getByUserId(user.getId());
        for (Order order : currentOrders) {
            if (order.getStatus() == OrderStatus.PROCESSING) {
                return order;
            }
        }
        // Если у пользователя нет текущего заказа, создаем новый
        Order newOrder = Order.builder()
                .user(user)
                .status(OrderStatus.PROCESSING)
                .createdAt(LocalDateTime.now())
                .build();
        return orderService.create(newOrder);
    }
}
