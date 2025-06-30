package com.example.restaurant.controller;

import com.example.restaurant.dto.MenuItemDto;
import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.mappers.MenuItemMapper;
import com.example.restaurant.repository.MenuItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    public MenuController(MenuItemRepository menuItemRepository, MenuItemMapper menuItemMapper) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemMapper = menuItemMapper;
    }

    @GetMapping
    public List<MenuItemDto> getAllMenuItems() {
        List<MenuItem> entities = menuItemRepository.findAll();
        return menuItemMapper.toDto(entities);  // use mapper here!
    }

    @PostMapping
    public MenuItemDto createMenuItem(@RequestBody MenuItemDto menuItemDto) {
        MenuItem menuItem = menuItemMapper.toEntity(menuItemDto);
        MenuItem saved = menuItemRepository.save(menuItem);
        return menuItemMapper.toDto(saved);
    }
}

