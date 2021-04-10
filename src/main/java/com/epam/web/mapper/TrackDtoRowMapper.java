package com.epam.web.mapper;

import com.epam.web.commands.trackCommands.EditTrackFormCommand;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Artist;
import com.epam.web.entities.Entity;
import com.epam.web.entities.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TrackDtoRowMapper extends Entity implements RowMapper<TrackDto> {
    private static final Logger LOGGER = LogManager.getLogger(EditTrackFormCommand.class);

    @Override
    public TrackDto map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Track.ID);
        LocalDate releaseDate = resultSet.getObject(Track.RELEASE_DATE, LocalDate.class);
        String title = resultSet.getString(Track.TITLE);
        String description = resultSet.getString(Track.DESCRIPTION);
        BigDecimal price = resultSet.getBigDecimal(Track.PRICE);
        String filename = resultSet.getString(Track.FILENAME);
        Long artistId = resultSet.getLong(Artist.ID);
        String artistName = resultSet.getString(Artist.NAME);

        return new TrackDto.Builder()
                .id(id)
                .releaseDate(releaseDate)
                .title(title)
                .description(description)
                .price(price)
                .filename(filename)
                .artistId(artistId)
                .artistName(artistName)
                .build();
    }
}
