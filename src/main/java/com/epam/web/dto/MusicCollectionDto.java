package com.epam.web.dto;

import com.epam.web.entities.MusicCollectionType;

import java.time.LocalDate;

public class MusicCollectionDto extends Dto {

    private Long id;
    private MusicCollectionType type;
    private LocalDate releaseDate;
    private String title;
    private Long artistId;
    private String artistName;
    //  private BigDecimal price;

    @Override
    public Long getId() {
        return id;
    }

    public MusicCollectionType getType() {
        return type;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public Long getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public static class Builder {
        private MusicCollectionDto musicCollectionDto;

        public Builder() {
            musicCollectionDto = new MusicCollectionDto();
        }

        public MusicCollectionDto.Builder id(Long id) {
            musicCollectionDto.id = id;
            return this;
        }

        public MusicCollectionDto.Builder type(MusicCollectionType type) {
            musicCollectionDto.type = type;
            return this;
        }

        public MusicCollectionDto.Builder releaseDate(LocalDate releaseDate) {
            musicCollectionDto.releaseDate = releaseDate;
            return this;
        }

        public MusicCollectionDto.Builder title(String title) {
            musicCollectionDto.title = title;
            return this;
        }

        public MusicCollectionDto.Builder artistId(Long artistId) {
            musicCollectionDto.artistId = artistId;
            return this;
        }

        public MusicCollectionDto.Builder artistName(String artistName) {
            musicCollectionDto.artistName = artistName;
            return this;
        }


        public MusicCollectionDto build() {
            return musicCollectionDto;
        }

    }

}
