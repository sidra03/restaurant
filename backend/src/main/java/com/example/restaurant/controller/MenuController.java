package com.example.restaurant.controller;

import com.example.restaurant.dto.MenuItemDto;
import com.example.restaurant.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/menu")
    public List<MenuItemDto> getMenu() {
        return menuService.getAllMenuItems();
    }

    @GetMapping("/test")
    public String test() {
        return "App is up";
    }

}
