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
        BigDecimal price = resultSet.getBigDecimal(Track.PRICE);
        String filename = resultSet.getString(Track.FILENAME);
        return new Track(id, releaseDate, title, price, filename);
    }
}
