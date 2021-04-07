package com.epam.web.mapper;

import com.epam.web.dto.MusicCollectionDto;
import com.epam.web.entities.Artist;
import com.epam.web.entities.MusicCollection;
import com.epam.web.entities.MusicCollectionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MusicCollectionDtoRowMapper implements RowMapper<MusicCollectionDto> {
    private static final String TYPE_ALBUM = "ALBUM";

    @Override
    public MusicCollectionDto map(ResultSet resultSet) throws SQLException {
        MusicCollectionDto musicCollection;
        Long artistId;
        String artistName;
        Long id = resultSet.getLong(MusicCollection.ID);
        String type = resultSet.getString(MusicCollection.COLLECTION_TYPE);
        LocalDate releaseDate = resultSet.getObject(MusicCollection.RELEASE_DATE, LocalDate.class);
        String title = resultSet.getString(MusicCollection.TITLE);

        if (type.equals(TYPE_ALBUM)) {
            artistId = resultSet.getLong(Artist.ID);
            artistName = resultSet.getString(Artist.NAME);
            musicCollection = new MusicCollectionDto.Builder()
                    .id(id)
                    .type(MusicCollectionType.valueOf(type))
                    .releaseDate(releaseDate)
                    .title(title)
                    .artistId(artistId)
                    .artistName(artistName)
                    .build();
        } else {
            musicCollection = new MusicCollectionDto.Builder()
                    .id(id)
                    .type(MusicCollectionType.valueOf(type))
                    .releaseDate(releaseDate)
                    .title(title)
                    .build();
        }
        return musicCollection;
    }

}
