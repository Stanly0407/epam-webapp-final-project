package com.epam.web.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Track extends Entity {

    public static final String TABLE = "track";
    public static final String ID = "id";
    public static final String RELEASE_DATE = "release_date";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String FILENAME = "filename";
    public static final String ARTIST_ID = "artist_id";

    private LocalDate releaseDate;
    private String title;
    private String description;
    private BigDecimal price;
    private String filename;
    private Long artistId;

    public Track(Long id, LocalDate releaseDate, String title, String description, BigDecimal price, String filename, Long artistId) {
        super(id);
        this.releaseDate = releaseDate;
        this.title = title;
        this.description = description;
        this.price = price;
        this.filename = filename;
        this.artistId = artistId;
    }

    public Track(String title, String description, BigDecimal price, String filename) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.filename = filename;
    }

    public Track() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    @Override
    public String toString() {
        return "Track{" +
                "releaseDate=" + releaseDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", filename='" + filename + '\'' +
                ", artistId=" + artistId +
                '}';
    }
}
