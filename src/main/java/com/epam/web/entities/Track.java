package com.epam.web.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Track extends Entity {

    public static final String TABLE = "track";
    public static final String ID = "id";
    public static final String RELEASE_DATE = "release_date";
    public static final String TITLE = "title";
    public static final String PRICE = "price";

    private LocalDate releaseDate;
    private String title;
    private BigDecimal price;

    public Track(Long id, LocalDate releaseDate, String title, BigDecimal price) {
        super(id);
        this.releaseDate = releaseDate;
        this.title = title;
        this.price = price;
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

}
