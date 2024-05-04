package com.example.onlinelearningplatform.service.implementations;

import com.example.onlinelearningplatform.exception.NullEntityReferenceException;
import com.example.onlinelearningplatform.models.Order;
import com.example.onlinelearningplatform.repos.OrderRepository;
import com.example.onlinelearningplatform.service.services.DishService;
import com.example.onlinelearningplatform.service.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import com.example.onlinelearningplatform.models.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final DishService dishService;

    @Override
    public Order create(Order order) {
        if (order != null) {
            calculateTotalPrice(order);
            return orderRepository.save(order);
        }
        throw new NullEntityReferenceException("Order cannot be 'null'");
    }


    @Override
    public Order readById(long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    public Order update(Order order) {
        if (order != null) {
            readById(order.getId());
            calculateTotalPrice(order);
            return orderRepository.save(order);
        }
        throw new NullEntityReferenceException("Order cannot be 'null'");
    }

    @Override
    public void delete(long id) {
        orderRepository.delete(readById(id));
    }

    @Override
    public List<Order> getByUserId(long userId) {
        List<Order> orders = orderRepository.getByUserId(userId);
        return orders.isEmpty() ? new ArrayList<>() : orders;
    }

    @Override
    public Order addItemToOrder(long orderId, long dishId) {
        Order order = readById(orderId);
        Dish dish = dishService.readById(dishId);
        order.getItems().add(dish);
        order.setTotalPrice(calculateTotalPrice(order));
        return orderRepository.save(order);
    }
    // Пример дополнительной логики - расчет общей стоимости заказа
    private double calculateTotalPrice(Order order) {
        double totalPrice = order.getItems().stream()
                .mapToDouble(Dish::getPrice)
                .sum();
        order.setTotalPrice(totalPrice);
        return totalPrice;
    }
}
