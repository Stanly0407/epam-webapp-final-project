package com.epam.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TrackDto extends Dto {
    public static final String TABLE = ""; // change with Track logic
    private Long id;
    private LocalDate releaseDate;
    private String title;
    private String description;
    private BigDecimal price;
    private String filename;
    private Long artistId;
    private String artistName;


    public Long getId() {
        return id;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getFilename() {
        return filename;
    }

    public Long getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }


    public static class Builder {
        private TrackDto newTrack;

        public Builder() {
            newTrack = new TrackDto();
        }

        public Builder id(Long id) {
            newTrack.id = id;
            return this;
        }

        public Builder releaseDate(LocalDate releaseDate) {
            newTrack.releaseDate = releaseDate;
            return this;
        }

        public Builder title(String title) {
            newTrack.title = title;
            return this;
        }

        public Builder description(String description) {
            newTrack.description = description;
            return this;
        }

        public Builder price(BigDecimal price) {
            newTrack.price = price;
            return this;
        }

        public Builder filename(String filename) {
            newTrack.filename = filename;
            return this;
        }

        public Builder artistId(Long artistId) {
            newTrack.artistId = artistId;
            return this;
        }

        public Builder artistName(String artistName) {
            newTrack.artistName = artistName;
            return this;
        }

        public TrackDto build() {
            return newTrack;
        }
    }

}
