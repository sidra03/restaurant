package com.example.restaurant.service;

import com.example.restaurant.dto.MenuItemDto;
import com.example.restaurant.mappers.MenuItemMapper;
import com.example.restaurant.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    public List<MenuItemDto> getAllMenuItems() {
        return menuItemMapper.toDto(menuItemRepository.findAll());
    }
}
