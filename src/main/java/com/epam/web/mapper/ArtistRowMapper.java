package com.epam.web.mapper;

import com.epam.web.entities.Artist;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistRowMapper implements RowMapper<Artist> {

    @Override
    public Artist map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Artist.ID);
        String name = resultSet.getString(Artist.NAME);
        String lastname = resultSet.getString(Artist.LASTNAME);
        return new Artist(id, name, lastname);
    }
}
