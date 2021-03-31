package com.epam.web.entities;

public enum TracksCollectionType {
    COLLECTION("COLLECTION"),
    ALBUM("ALBUM");

    private String value;

    TracksCollectionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
