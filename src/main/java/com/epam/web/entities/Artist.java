package com.epam.web.entities;

public class Artist extends Entity {

    public static final String TABLE = "artist";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String LASTNAME = "lastname";

    private String name;
    private String lastname;

    public Artist(Long id, String name, String lastname) {
        super(id);
        this.name = name;
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
