package com.epam.web.entities;

import java.math.BigDecimal;

public class TrackAndArtist extends Track {

    public static final String ARTIST_NAME = "name";
    public static final String ARTIST_LASTNAME = "lastname";

    private String artistName;
    private String artistLastname;

    public TrackAndArtist(String title, String description, BigDecimal price, String filename,  String artistName, String artistLastname) {
        super(title, description, price, filename);
        this.artistName = artistName;
        this.artistLastname = artistLastname;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistLastname() {
        return artistLastname;
    }

    public void setArtistLastname(String artistLastname) {
        this.artistLastname = artistLastname;
    }
}
