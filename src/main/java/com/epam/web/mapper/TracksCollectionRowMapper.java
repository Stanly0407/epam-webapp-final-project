package com.epam.web.mapper;

import com.epam.web.entities.TracksCollection;
import com.epam.web.entities.TracksCollectionType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TracksCollectionRowMapper implements RowMapper<TracksCollection> {

    @Override
    public TracksCollection map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(TracksCollection.ID);
        String type = resultSet.getString(TracksCollection.COLLECTION_TYPE);
        String title = resultSet.getString(TracksCollection.TITLE);
        Long artistId = resultSet.getLong(TracksCollection.ARTIST_ID);
        return new TracksCollection(id, TracksCollectionType.valueOf(type), title, artistId);
    }
}
