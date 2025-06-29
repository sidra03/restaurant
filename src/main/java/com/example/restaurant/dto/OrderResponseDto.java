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
    private List<MenuItemDto> items;
    private double total;
    private String status;
    private boolean hot; // if the order is hot or not
    private int totalChillies; // total chillies in order
    private double discount; // 0 if no discount else 10% discount applied
    private String message;
}
