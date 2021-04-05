package com.epam.web.entities;

public enum MusicCollectionType {
    COLLECTION("COLLECTION"),
    ALBUM("ALBUM");

    private String value;

    MusicCollectionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
