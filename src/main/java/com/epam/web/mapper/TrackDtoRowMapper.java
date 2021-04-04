package com.epam.web.mapper;

import com.epam.web.commands.trackCommands.ShowEditTrackFormCommand;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Artist;
import com.epam.web.entities.Entity;
import com.epam.web.entities.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackDtoRowMapper extends Entity implements RowMapper<TrackDto> {
    private static final Logger LOGGER = LogManager.getLogger(ShowEditTrackFormCommand.class);

    @Override
    public TrackDto map(ResultSet resultSet) throws SQLException {

        Long id = resultSet.getLong(Track.ID);
        String title = resultSet.getString(Track.TITLE);
        String description = resultSet.getString(Track.DESCRIPTION);
        BigDecimal price = resultSet.getBigDecimal(Track.PRICE);
                String filename = resultSet.getString(Track.FILENAME);
        Long artistId = resultSet.getLong(Artist.ID);
        String artistName = resultSet.getString(Artist.NAME);
        String artistLastname = resultSet.getString(Artist.LASTNAME);

        return new TrackDto.Builder()
                .id(id)
                .title(title)
                .description(description)
                .price(price)
                .filename(filename)
                .artistId(artistId)
                .artistName(artistName)
                .artistLastname(artistLastname)
                .build();
    }
}
