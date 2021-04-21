package com.epam.web.dao;

import com.epam.web.entities.Artist;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;


public class ArtistDao extends AbstractDao<Artist> implements Dao<Artist> {

    private static final String FIND_ARTIST_BY_ID = "SELECT id, name FROM artist WHERE id = ?";
    private static final String FIND_ARTISTS_BY_TRACK_ID = "SELECT a.id, a.name FROM artist a INNER JOIN track_artist ta " +
            "ON (a.id=ta.artist_id) WHERE track_id = ?";
    private static final String FIND_ALL_ARTISTS = "SELECT id, name FROM artist";
    private static final String UPDATE_ARTIST = "UPDATE artist SET name = ? where id = ?";

    public ArtistDao(Connection connection, RowMapper<Artist> mapper) {
        super(connection, mapper);
    }

    public List<Artist> getAll() throws DaoException {
        return executeQuery(FIND_ALL_ARTISTS);
    }

    public List<Artist> getByTrackId(Long id) throws DaoException {
        return executeQuery(FIND_ARTISTS_BY_TRACK_ID, id);
    }

    public void updateName(String newName, Long id) throws DaoException {
        executeUpdate(UPDATE_ARTIST, newName, id);
    }

    @Override
    public Optional<Artist> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_ARTIST_BY_ID, id);
    }

    @Override
    public void save(Artist entity) {
    }

    @Override
    public void removeById(Long id) {
    }

    @Override
    protected String getTableName() {
        return Artist.TABLE;
    }
}
