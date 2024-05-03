package com.example.onlinelearningplatform.service.services;
import com.example.onlinelearningplatform.models.Order;

import java.util.List;

public interface OrderService {
    Order create(Order order);
    Order readById(long id);
    Order update(Order order);
    void delete(long id);
    List<Order> getByUserId(long userId);
}
