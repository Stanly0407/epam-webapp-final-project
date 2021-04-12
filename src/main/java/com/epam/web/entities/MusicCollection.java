package com.epam.web.entities;

import java.time.LocalDate;


public class MusicCollection extends Entity {

    public static final String TABLE = "collection";
    public static final String ID = "id";
    public static final String COLLECTION_TYPE = "type";
    public static final String RELEASE_DATE = "release_date";
    public static final String TITLE = "title";


    private MusicCollectionType type;
    private LocalDate releaseDate;
    private String title;
    private Artist artist;

    public MusicCollection(Long id, MusicCollectionType type, LocalDate releaseDate, String title, Artist artist) {
        super(id);
        this.type = type;
        this.releaseDate = releaseDate;
        this.title = title;
        this.artist = artist;
    }

    public MusicCollection(Long id, MusicCollectionType type, LocalDate releaseDate, String title) {
        super(id);
        this.type = type;
        this.releaseDate = releaseDate;
        this.title = title;
    }

    public MusicCollection(){
    }

    public MusicCollectionType getType() {
        return type;
    }

    public void setType(MusicCollectionType type) {
        this.type = type;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }


}
