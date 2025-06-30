package com.example.restaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
    DRINK,
    MAIN,
    SNACKS,
    DESSERT;

    @JsonCreator
    public static Category fromString(String key) {
        return key == null ? null : Category.valueOf(key.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase();
    }
}

