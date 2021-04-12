package com.epam.web.dao;

import com.epam.web.entities.Artist;
import com.epam.web.entities.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;


public class ArtistDao extends AbstractDao<Artist> implements Dao<Artist> {

    private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT id, login, name, lastname, role, balance FROM user WHERE login = ? AND password = ?";
    private static final String FIND_ARTIST_BY_TRACK_ID = "SELECT a.id, a.name FROM artist a INNER JOIN track_artist ta ON (a.id = ta.artist_id) INNER JOIN track t ON (t.id = ta.track_id) WHERE t.id = ?";
    private static final String FIND_ARTIST_BY_ID = "SELECT id, login, name, lastname, role, balance FROM user WHERE id = ?";

    private static final String UPDATE_BALANCE = "UPDATE user SET balance = ? where id = ?";

    public ArtistDao(Connection connection, RowMapper<Artist> mapper) {
        super(connection, mapper);
    }


    public List<Artist> getByTrackId(Long trackId) throws DaoException {
        return executeQuery(FIND_ARTIST_BY_TRACK_ID, trackId);
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
