package com.example.onlinelearningplatform.service.implementations;

import com.example.onlinelearningplatform.exception.NullEntityReferenceException;
import com.example.onlinelearningplatform.models.Order;
import com.example.onlinelearningplatform.repos.OrderRepository;
import com.example.onlinelearningplatform.service.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order create(Order order) {
        if (order != null) {
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
}
