package com.example.onlinelearningplatform.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishDto {
    private long id;
    private String name;
    private String description;
    private double servingWeight;
    private double price;
    private Long menuId;
}
