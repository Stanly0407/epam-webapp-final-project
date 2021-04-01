package com.epam.web.mapper;

import com.epam.web.entities.TrackAndArtist;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackAndArtistRowMapper extends TrackRowMapper {

    @Override
    public TrackAndArtist map(ResultSet resultSet) throws SQLException {
        String title = resultSet.getString(TrackAndArtist.TITLE);
        String description = resultSet.getString(TrackAndArtist.DESCRIPTION);
        BigDecimal price = resultSet.getBigDecimal(TrackAndArtist.PRICE);
        String filename = resultSet.getString(TrackAndArtist.FILENAME);
        String artistName = resultSet.getString(TrackAndArtist.ARTIST_NAME);
        String artistLastname = resultSet.getString(TrackAndArtist.ARTIST_LASTNAME);
        return new TrackAndArtist(title, description, price, filename, artistName, artistLastname);
    }
}
