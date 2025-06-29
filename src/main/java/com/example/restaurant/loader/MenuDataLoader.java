    package com.example.restaurant.loader;

    import com.example.restaurant.entity.MenuItem;
    import com.example.restaurant.repository.MenuItemRepository;
    import com.fasterxml.jackson.core.type.TypeReference;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import jakarta.annotation.PostConstruct;
    import lombok.RequiredArgsConstructor;
    import org.springframework.core.io.ClassPathResource;
    import org.springframework.stereotype.Component;
    import java.io.InputStream;
    import java.util.List;

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
                    List<MenuItem> menuItems = objectMapper.readValue(inputStream, new TypeReference<>() {});
                    menuItemRepository.saveAll(menuItems);
                    System.out.println("✅ Menu items loaded from JSON");
                    menuItems.forEach(System.out::println);  // debug print
                }
            } catch (Exception e) {
                System.err.println("⚠️ Failed to load menu items: " + e.getMessage());
            }
        }
    }
