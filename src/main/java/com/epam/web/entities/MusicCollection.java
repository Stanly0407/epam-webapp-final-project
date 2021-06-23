package com.epam.web.entities;

import java.time.LocalDate;
import java.util.Objects;


public class MusicCollection extends Entity {
    public static final String TABLE = "collection";
    public static final String ID = "id";
    public static final String COLLECTION_TYPE = "type";
    public static final String RELEASE_DATE = "release_date";
    public static final String TITLE = "title";
    public static final String FILENAME = "filename";

    private MusicCollectionType type;
    private LocalDate releaseDate;
    private String title;
    private Artist artist;
    private String filename;

    public MusicCollection() {
    }

    public MusicCollection(Long id, MusicCollectionType type, LocalDate releaseDate, String title, Artist artist, String filename) {
        super(id);
        this.type = type;
        this.releaseDate = releaseDate;
        this.title = title;
        this.artist = artist;
        this.filename = filename;
    }

    public MusicCollection(Long id, MusicCollectionType type, LocalDate releaseDate, String title, String filename) {
        super(id);
        this.type = type;
        this.releaseDate = releaseDate;
        this.title = title;
        this.filename = filename;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MusicCollection that = (MusicCollection) o;
        return type == that.type &&
                Objects.equals(releaseDate, that.releaseDate) &&
                Objects.equals(title, that.title) &&
                Objects.equals(artist, that.artist) &&
                Objects.equals(filename, that.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, releaseDate, title, artist, filename);
    }
}
