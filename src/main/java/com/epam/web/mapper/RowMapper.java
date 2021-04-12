package com.epam.web.mapper;

import com.epam.web.entities.Artist;
import com.epam.web.entities.Entity;
import com.epam.web.entities.Track;
import com.epam.web.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T extends Entity> {

    T map(ResultSet resultSet) throws SQLException;

    static RowMapper<? extends Entity> create(String table) {
        switch (table) {
            case User.TABLE:
                return new UserRowMapper();
            case Track.TABLE:
                return new TrackRowMapper();
            case Artist.TABLE:
                return new ArtistRowMapper();
            default:
                throw new IllegalArgumentException("Unknown table => " + table);
        }
    }


}
