package com.epam.web.dto;

import com.epam.web.entities.Artist;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TrackDto {

    private static final String PATH_PREFIX = "/musicwebapp/audio/";
    private Long id;
    private LocalDate releaseDate;
    private String title;
    private BigDecimal price;
    private String filename;
    private List<Artist> artists;
    private TrackStatus status;
    private int commentsAmount;

    public Long getId() {
        return id;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getFilename() {
        return filename;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public TrackStatus getStatus() {
        return status;
    }

    public int getCommentsAmount() {
        return commentsAmount;
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

        public Builder price(BigDecimal price) {
            newTrack.price = price;
            return this;
        }

        public Builder filename(String filename) {
            newTrack.filename = PATH_PREFIX + filename;
            return this;
        }

        public Builder artists(List<Artist> artists) {
            newTrack.artists = artists;
            return this;
        }

        public Builder status(TrackStatus status) {
            newTrack.status = status;
            return this;
        }

        public Builder commentsAmount(int commentsAmount) {
            newTrack.commentsAmount = commentsAmount;
            return this;
        }

        public TrackDto build() {
            return newTrack;
        }
    }

}
