package com.example.onlinelearningplatform.repos;

import com.example.onlinelearningplatform.models.Menu;
import com.example.onlinelearningplatform.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {

}
