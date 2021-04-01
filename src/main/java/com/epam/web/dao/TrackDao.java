package com.epam.web.dao;

import com.epam.web.entities.Track;
import com.epam.web.entities.TrackAndArtist;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;
import com.epam.web.mapper.TrackAndArtistRowMapper;
import com.epam.web.mapper.TrackRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TrackDao extends AbstractDao<Track> implements Dao<Track> {
    private static final Logger LOGGER = LogManager.getLogger(TrackDao.class);

    private static final String INSERT_TRACK = "INSERT INTO track (title, description, price, filename, artist_id) VALUE (?, ?, ?, ?, ?)";
    private static final String GET_TRACK_LIST = "SELECT t.title, t.description, t.price, t.filename, a.name, a.lastname FROM ? t INNER JOIN ? a ON t.artist_id=a.id;";


    public TrackDao(Connection connection, RowMapper<Track> mapper) {
        super(connection, mapper);
    }

    public void createTrack(String title, String description, BigDecimal price, String filename, Long artistId) throws DaoException {
        executeUpdate(INSERT_TRACK, new TrackRowMapper(), title, description, price, filename, artistId);
    }

//    public List<TrackAndArtist> getTrackList(String trackTable, String artistTable) throws DaoException {
//        return executeQuery(GET_TRACK_LIST, new TrackAndArtistRowMapper(), trackTable, artistTable);
//    }

    @Override
    public Optional<Track> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Track entity) {

    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    protected String getTableName() {
        return Track.TABLE;
    }
}
