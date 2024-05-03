package com.example.onlinelearningplatform.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDto {
    private long id;
    private String name;
    private String description;
    private double servingWeight;
    private double price;
}
