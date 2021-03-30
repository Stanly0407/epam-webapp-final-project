package com.epam.web.entities;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
