package com.example.restaurant.mapper;

import com.example.restaurant.dto.MenuItemDto;
import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.enums.Category;
import com.example.restaurant.enums.SpiceLevel;
import com.example.restaurant.mappers.MenuItemMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MenuItemMapperTest {

    @Autowired
    private MenuItemMapper mapper;

    @Test
    void testSpiceLevelToChilliesMapping() {
        MenuItem menuItem = MenuItem.builder()
                .id(1L)
                .name("Hot Curry")
                .price(20.0)
                .available(true)
                .category(Category.MAIN)
                .spiceLevel(SpiceLevel.HOT)
                .build();

        MenuItemDto dto = mapper.toDto(menuItem);

        assertNotNull(dto);
        assertEquals(3, dto.getChillies());
        assertEquals("MAIN", dto.getCategory());
    }

    @Test
    void testChilliesToSpiceLevelMapping() {
        MenuItemDto dto = MenuItemDto.builder()
                .id(2L)
                .name("Mild Soup")
                .price(10.0)
                .available(true)
                .category("SNACKS")
                .chillies(1)
                .build();

        MenuItem entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(SpiceLevel.MILD, entity.getSpiceLevel());
        assertEquals(Category.SNACKS, entity.getCategory());
    }
}


