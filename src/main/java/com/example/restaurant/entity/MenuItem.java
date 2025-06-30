package com.example.restaurant.entity;

import com.example.restaurant.enums.Category;
import com.example.restaurant.enums.SpiceLevel;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private boolean available;
    private Category category;

    @Enumerated(EnumType.STRING)
    private SpiceLevel spiceLevel;
}
