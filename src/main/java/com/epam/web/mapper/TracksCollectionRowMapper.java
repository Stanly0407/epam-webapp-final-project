package com.epam.web.mapper;

import com.epam.web.entities.MusicCollection;
import com.epam.web.entities.MusicCollectionType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TracksCollectionRowMapper implements RowMapper<MusicCollection> {

    @Override
    public MusicCollection map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(MusicCollection.ID);
        String type = resultSet.getString(MusicCollection.COLLECTION_TYPE);
        LocalDate releaseDate = resultSet.getObject(MusicCollection.RELEASE_DATE, LocalDate.class);
        String title = resultSet.getString(MusicCollection.TITLE);
        Long artistId = resultSet.getLong(MusicCollection.ARTIST_ID);
        return new MusicCollection(id, MusicCollectionType.valueOf(type), releaseDate, title, artistId);
    }
}
