package com.epam.web.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Track extends Entity {

    public static final String TABLE = "track";
    public static final String ID = "id";
    public static final String RELEASE_DATE = "release_date";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String FILENAME = "filename";
    public static final String ARTISTS = "name";
    public static final String IS_PAID = "is_paid";

    private LocalDate releaseDate;
    private String title;
    private String description;
    private BigDecimal price;
    private String filename;
    private List<Artist> artists;
    private boolean isPaid;

    public Track(Long id, LocalDate releaseDate, String title, String description, BigDecimal price, String filename, List<Artist> artists, boolean isPaid) {
        super(id);
        this.releaseDate = releaseDate;
        this.title = title;
        this.description = description;
        this.price = price;
        this.filename = filename;
        this.artists = artists;
        this.isPaid = isPaid;
    }

    public Track(String title, String description, BigDecimal price, String filename) {
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

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
