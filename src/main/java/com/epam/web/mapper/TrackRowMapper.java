package com.epam.web.mapper;

import com.epam.web.entities.Track;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TrackRowMapper implements RowMapper<Track> {

    @Override
    public Track map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Track.ID);
        LocalDate releaseDate = resultSet.getObject(Track.RELEASE_DATE, LocalDate.class);
        String title = resultSet.getString(Track.TITLE);
        String description = resultSet.getString(Track.DESCRIPTION);
        BigDecimal price = resultSet.getBigDecimal(Track.PRICE);
        String filename = resultSet.getString(Track.FILENAME);
        Long artistId = resultSet.getLong(Track.ARTIST_ID);

        return new Track(id, releaseDate, title, description, price, filename, artistId);
    }
}
