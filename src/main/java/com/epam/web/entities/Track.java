package com.epam.web.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Track extends Entity {
    public static final String TABLE = "track";
    public static final String ID = "id";
    public static final String RELEASE_DATE = "release_date";
    public static final String TITLE = "title";
    public static final String PRICE = "price";
    public static final String FILENAME = "filename";

    private LocalDate releaseDate;
    private String title;
    private BigDecimal price;
    private String filename;

    public Track() {
    }

    public Track(Long id, LocalDate releaseDate, String title, BigDecimal price, String filename) {
        super(id);
        this.releaseDate = releaseDate;
        this.title = title;
        this.price = price;
        this.filename = filename;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Track track = (Track) o;
        return Objects.equals(releaseDate, track.releaseDate) &&
                Objects.equals(title, track.title) &&
                Objects.equals(price, track.price) &&
                Objects.equals(filename, track.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(releaseDate, title, price, filename);
    }

}
