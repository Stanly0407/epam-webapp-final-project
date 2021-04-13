package com.epam.web.dto;

import com.epam.web.entities.Artist;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TrackDto {
    private Long id;
    private LocalDate releaseDate;
    private String title;
    private BigDecimal price;
    private List<Artist> artists;
    private TrackStatusEnum status;

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

    public List<Artist> getArtists() {
        return artists;
    }

    public TrackStatusEnum getStatus() {
        return status;
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

        public Builder artists(List<Artist> artists) {
            newTrack.artists = artists;
            return this;
        }

        public Builder status(TrackStatusEnum status) {
            newTrack.status = status;
            return this;
        }

        public TrackDto build() {
            return newTrack;
        }
    }

}
