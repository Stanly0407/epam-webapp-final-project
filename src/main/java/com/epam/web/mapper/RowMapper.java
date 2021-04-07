package com.epam.web.mapper;

import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Artist;
import com.epam.web.entities.Entity;
import com.epam.web.entities.Track;
import com.epam.web.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper <T extends Entity> {

    T map(ResultSet resultSet) throws SQLException;

    static RowMapper<? extends Entity> create(String table) {
        switch (table){
            case User.TABLE: return new UserRowMapper();
            case Track.TABLE: return new TrackRowMapper(); //delete, change on TrackDto
            case Artist.TABLE: return new ArtistRowMapper();
            case TrackDto.TABLE: return new TrackDtoRowMapper();
            default:
                throw new IllegalArgumentException("Unknown table => " + table);
        }
    }

}
