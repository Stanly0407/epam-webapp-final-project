package com.epam.web.entities;

import java.math.BigDecimal;

public class Track extends Entity {

    public static final String TABLE = "track";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String ARTIST_ID = "artist_id";

    private String title;
    private String description;
    private BigDecimal price;
    private Long artistId;

    public Track(Long id, String title, String description, BigDecimal price, Long artistId) {
        super(id);
        this.title = title;
        this.description = description;
        this.price = price;
        this.artistId = artistId;
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

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    @Override
    public String toString() {
        return "Track{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", artistId=" + artistId +
                '}';
    }
}
