package com.kosuri.rxkolan.entity;

import java.util.HashMap;
import java.util.Map;

public enum ServiceOfferedEnum {

    AM("Ambulance");

    private final String description;

    // Reverse-lookup map for getting a action from an description
    private static final Map<String, ServiceOfferedEnum> lookup = new HashMap<>();

    static {
        for (ServiceOfferedEnum d : ServiceOfferedEnum.values()) {
            lookup.put(d.getDescription(), d);
        }
    }

    private ServiceOfferedEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ServiceOfferedEnum get(String description) {
        return lookup.get(description);
    }

}
