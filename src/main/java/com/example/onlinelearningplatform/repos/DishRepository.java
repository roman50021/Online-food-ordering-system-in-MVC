package com.example.onlinelearningplatform.repos;

import com.example.onlinelearningplatform.models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dish,Long> {

}
