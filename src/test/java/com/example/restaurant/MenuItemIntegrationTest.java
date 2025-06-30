package com.example.restaurant;

import com.example.restaurant.dto.MenuItemDto;
import com.example.restaurant.repository.MenuItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MenuItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    public void setup() {
        // Clean database before each test
        menuItemRepository.deleteAll();
    }

    @Test
    public void testCreateAndGetMenuItem() throws Exception {
        MenuItemDto newItem = MenuItemDto.builder()
                .name("Spicy Chicken Wings")
                .description("Hot and spicy wings with chili sauce")
                .price(12.99)
                .available(true)
                .chillies(3)
                .category("MAIN")  // Use uppercase to match enum constant
                .build();

        // Create a new menu item via POST
        mockMvc.perform(post("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spicy Chicken Wings"))
                .andExpect(jsonPath("$.chilliLevel").value(3))
                .andExpect(jsonPath("$.category").value("MAIN"));  // Match uppercase

        // Fetch all menu items and verify the created item is there
        mockMvc.perform(get("/menu"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Spicy Chicken Wings"))
                .andExpect(jsonPath("$[0].chilliLevel").value(3))
                .andExpect(jsonPath("$[0].category").value("MAIN"));

    }

    @Test
    public void testGetMenuItemNotFound() throws Exception {
        mockMvc.perform(get("/menu/999"))
                .andExpect(status().isNotFound());
    }
}