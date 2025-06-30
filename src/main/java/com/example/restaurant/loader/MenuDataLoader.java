    package com.example.restaurant.loader;

    import com.example.restaurant.dto.MenuItemDto;
    import com.example.restaurant.entity.MenuItem;
    import com.example.restaurant.enums.Category;
    import com.example.restaurant.enums.SpiceLevel;
    import com.example.restaurant.repository.MenuItemRepository;
    import com.fasterxml.jackson.core.type.TypeReference;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import jakarta.annotation.PostConstruct;
    import lombok.RequiredArgsConstructor;
    import org.springframework.core.io.ClassPathResource;
    import org.springframework.stereotype.Component;

    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.io.InputStream;
    import java.util.Arrays;
    import java.util.List;
    import java.util.stream.Collectors;

    @Component
    @RequiredArgsConstructor
    public class MenuDataLoader {

        private final MenuItemRepository menuItemRepository;
        private final ObjectMapper objectMapper;

        @PostConstruct
        public void loadMenuItems() {
            try {
                if (menuItemRepository.count() == 0) {
                    InputStream inputStream = new ClassPathResource("menu.json").getInputStream();

                    List<MenuItemDto> dtos = objectMapper.readValue(inputStream, new TypeReference<>() {});

                    List<MenuItem> menuItems = dtos.stream()
                            .map(dto -> MenuItem.builder()
                                    .name(dto.getName())
                                    .description(dto.getDescription())
                                    .price(dto.getPrice())
                                    .available(dto.isAvailable())
                                    .spiceLevel(SpiceLevel.fromChillies(dto.getChillies()))
                                    .category(Category.fromString(dto.getCategory())) // optional if enum
                                    .build())
                            .toList();

                    menuItemRepository.saveAll(menuItems);
                    System.out.println("✅ Menu items loaded from JSON");
                    menuItems.forEach(System.out::println);
                }
            } catch (Exception e) {
                System.err.println("⚠️ Failed to load menu items: " + e.getMessage());
            }
        }
    }
