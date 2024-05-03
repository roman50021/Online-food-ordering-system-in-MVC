package com.example.onlinelearningplatform.repos;

import com.example.onlinelearningplatform.models.Order;
import com.example.onlinelearningplatform.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> getByUserId(long userId);
}
