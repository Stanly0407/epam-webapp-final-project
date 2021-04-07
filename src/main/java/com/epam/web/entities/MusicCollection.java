package com.epam.web.entities;

import java.time.LocalDate;

public class MusicCollection extends Entity {

    public static final String TABLE = "collection";
    public static final String ID = "id";
    public static final String COLLECTION_TYPE = "type";
    public static final String RELEASE_DATE = "release_date";
    public static final String TITLE = "title";
    public static final String ARTIST_ID = "artist_id";

    private MusicCollectionType type;
    private LocalDate releaseDate;
    private String title;
    private Long artistId;

    public MusicCollection(Long id, MusicCollectionType type, LocalDate releaseDate, String title, Long artistId) {
        super(id);
        this.type = type;
        this.releaseDate = releaseDate;
        this.title = title;
        this.artistId = artistId;
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

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

}
