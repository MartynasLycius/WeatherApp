package com.proit.weather.enums;

public enum RoleType {
    ADMIN("ROLE_ADMIN");

    private final String label;

    private RoleType(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
