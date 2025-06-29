package com.example.restaurant.mappers;

import com.example.restaurant.dto.OrderResponseDto;
import com.example.restaurant.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MenuItemMapper.class)
public interface OrderMapper {
    OrderResponseDto toDto(Order order);
}
