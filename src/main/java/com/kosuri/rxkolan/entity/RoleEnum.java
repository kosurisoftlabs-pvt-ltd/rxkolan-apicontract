package com.kosuri.rxkolan.entity;

import java.util.HashMap;
import java.util.Map;

public enum RoleEnum {
    Admin("Admin"),
    Ambulance("Ambulance");

    private final String description;

    // Reverse-lookup map for getting a day from an abbreviation
    private static final Map<String, RoleEnum> lookup = new HashMap<String, RoleEnum>();

    static {
        for (RoleEnum d : RoleEnum.values()) {
            lookup.put(d.getDescription(), d);
        }
    }

    private RoleEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static RoleEnum get(String description) {
        return lookup.get(description);
    }
}
