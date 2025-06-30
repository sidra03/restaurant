package com.example.restaurant.enums;

import lombok.Getter;

@Getter
public enum SpiceLevel {
    NONE(0),
    MILD(1),
    MEDIUM(2),
    HOT(3);

    private final int chillies;

    SpiceLevel(int chillies) {
        this.chillies = chillies;
    }

    public static SpiceLevel fromChillies(int chillies) {
        if (chillies <= 0) return NONE;
        else if (chillies == 1) return MILD;
        else if (chillies == 2) return MEDIUM;
        else if (chillies == 3) return HOT;
        else return NONE;
    }
}
