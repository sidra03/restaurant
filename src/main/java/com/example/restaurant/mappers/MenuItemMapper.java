package com.example.restaurant.mappers;

import com.example.restaurant.dto.MenuItemDto;
import com.example.restaurant.entity.MenuItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    MenuItemDto toDto(MenuItem entity);
    List<MenuItemDto> toDto(List<MenuItem> entities);
}
