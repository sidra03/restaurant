package com.example.restaurant.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private String customerName;
    private Long id;
    private List<MenuItemDto> orderedItems;
    private double total;
    private String status;
    private boolean hotOrder; // if the order is hot or not
    private int chillies;
    private double discount;
    private String message;
}
