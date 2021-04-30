package com.epam.web.mapper;

import com.epam.web.entities.Artist;
import com.epam.web.entities.MusicCollection;
import com.epam.web.entities.MusicCollectionType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MusicCollectionRowMapper implements RowMapper<MusicCollection> {
    private static final String TYPE_ALBUM = "ALBUM";

    @Override
    public MusicCollection map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(MusicCollection.ID);
        String typeString = resultSet.getString(MusicCollection.COLLECTION_TYPE);
        MusicCollectionType type = MusicCollectionType.valueOf(typeString);
        LocalDate releaseDate = resultSet.getObject(MusicCollection.RELEASE_DATE, LocalDate.class);
        String title = resultSet.getString(MusicCollection.TITLE);
        String filename = resultSet.getString(MusicCollection.FILENAME);
        MusicCollection musicCollection;
        if (typeString.equals(TYPE_ALBUM)) {
            Long artistId = resultSet.getLong(Artist.ID);
            String name = resultSet.getString(Artist.NAME);
            String artistFilename = resultSet.getString(Artist.FILENAME);
            Artist artist = new Artist(artistId, name, artistFilename);
            musicCollection = new MusicCollection(id, type, releaseDate, title, artist, filename);
        } else {
            musicCollection = new MusicCollection(id, type, releaseDate, title, filename);
        }
        return musicCollection;
    }

}
