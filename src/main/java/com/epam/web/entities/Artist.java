package com.epam.web.entities;

public class Artist extends Entity {

    public static final String TABLE = "artist";
    public static final String ID = "id";
    public static final String NAME = "name";

    private String name;

    public Artist() {
    }
    
    public Artist(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
