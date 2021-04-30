package com.epam.web.entities;

public class Artist extends Entity {

    public static final String TABLE = "artist";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String FILENAME = "filename";

    private String filename;
    private String name;

    public Artist() {
    }
    
    public Artist(Long id, String name, String filename) {
        super(id);
        this.name = name;
        this.filename = filename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
