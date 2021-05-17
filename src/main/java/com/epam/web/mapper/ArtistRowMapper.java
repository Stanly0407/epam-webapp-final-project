package com.epam.web.mapper;

import com.epam.web.entities.Artist;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistRowMapper implements RowMapper<Artist> {
    private static final String PATH_PREFIX = "/musicwebapp/artists/";

    @Override
    public Artist map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Artist.ID);
        String name = resultSet.getString(Artist.NAME);
        String filename = PATH_PREFIX + resultSet.getString(Artist.FILENAME);
        return new Artist(id, name, filename);
    }
}
