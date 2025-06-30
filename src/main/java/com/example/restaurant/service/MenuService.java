package com.example.restaurant.service;

import com.example.restaurant.dto.MenuItemDto;
import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.mappers.MenuItemMapper;
import com.example.restaurant.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    public List<MenuItemDto> getAllMenuItems() {
        return menuItemMapper.toDto(menuItemRepository.findAll());
    }

    public Map<String, List<MenuItemDto>> getMenuGroupedByCategory() {
        List<MenuItemDto> allItems = menuItemMapper.toDto(menuItemRepository.findAll());
        return allItems.stream()
                .collect(Collectors.groupingBy(item ->
                        item.getCategory() != null ? item.getCategory() : "uncategorized"
                ));
    }

    public MenuItemDto createMenuItem(MenuItemDto dto) {
        MenuItem entity = menuItemMapper.toEntity(dto);
        MenuItem savedEntity = menuItemRepository.save(entity);
        return menuItemMapper.toDto(savedEntity);
    }
}



