package com.epam.web.mapper;

import com.epam.web.entities.TrackAndCollection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackAndCollectionRowMapper implements RowMapper<TrackAndCollection> {

    @Override
    public TrackAndCollection map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(TrackAndCollection.ID);
        Long trackId = resultSet.getLong(TrackAndCollection.TRACK_ID);
        Long collectionId = resultSet.getLong(TrackAndCollection.COLLECTION_ID);
        return new TrackAndCollection(id, trackId, collectionId);
    }
}
