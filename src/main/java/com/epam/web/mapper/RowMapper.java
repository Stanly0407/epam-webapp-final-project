package com.epam.web.mapper;

import com.epam.web.entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;


//RowMapper interface allows to map a row of the relations with the instance of entity class. It iterates the ResultSet internally and adds it into the collection.
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
            case Comment.TABLE:
                return new CommentRowMapper();
            default:
                throw new IllegalArgumentException("Unknown table => " + table);
        }
    }


}
