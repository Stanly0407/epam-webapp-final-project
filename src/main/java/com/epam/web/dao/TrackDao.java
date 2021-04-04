package com.epam.web.dao;

import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Artist;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;
import com.epam.web.mapper.TrackDtoRowMapper;
import com.epam.web.mapper.TrackRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TrackDao extends AbstractDao<TrackDto> implements Dao<TrackDto> {
    private static final Logger LOGGER = LogManager.getLogger(TrackDao.class);
    private static final String UPDATE_TRACK = "UPDATE track SET title=?, description=?, price=?, filename=? where id=?";
    private static final String INSERT_TRACK = "INSERT INTO track (id, title, description, price, filename, artist_id) VALUE (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_TRACK_LIST = "SELECT t.id, t.title, t.description, t.price, t.filename, a.id, " +
            "a.name, a.lastname FROM track t INNER JOIN artist a ON (t.artist_id = a.id)";
    private static final String FIND_TRACK = "SELECT t.id, t.title, t.description, t.price, t.filename, a.id, a.name, a.lastname " +
                                                 "FROM track t INNER JOIN artist a ON t.artist_id=a.id WHERE t.id=?";


    public TrackDao(Connection connection, RowMapper<TrackDto> mapper) {
        super(connection, mapper);
    }

    public void editTrack(String title, String description, String price, String filename, String id) throws DaoException {
        executeUpdate(UPDATE_TRACK, title, description, price, filename, id);
    }


    @Override
    public Optional<TrackDto> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_TRACK, new TrackDtoRowMapper(), id);
    }

    public List<TrackDto> getAllTracks() throws DaoException {
        return executeQuery(GET_TRACK_LIST, new TrackDtoRowMapper());
    }

    @Override
    public void save(TrackDto entity) {

    }

    @Override
    public void removeById(Long id) {

    }

    @Override   // todo remove this method because all select queries are join queries
    protected String getTableName() {
        return Track.TABLE;
    }
}
