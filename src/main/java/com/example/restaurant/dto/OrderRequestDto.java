package com.example.restaurant.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {
    private String customerName;
    private List<Long> itemIds;
}
