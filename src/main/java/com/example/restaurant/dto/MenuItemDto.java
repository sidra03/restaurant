package com.example.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private boolean available;
    @JsonProperty("chilliLevel")
    private int chillies;
    private String category;
}
