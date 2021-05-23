package com.epam.web.dao;

import com.epam.web.entities.Artist;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;


public class ArtistDao extends AbstractDao<Artist> implements Dao<Artist> {

    private static final String FIND_ARTIST_BY_ID = "SELECT id, name, filename FROM artist WHERE id = ?";
    private static final String FIND_ARTIST_BY_NAME = "SELECT id, name, filename FROM artist WHERE name = ?";
    private static final String FIND_ARTISTS_BY_TRACK_ID = "SELECT a.id, a.name, a.filename FROM artist a INNER JOIN track_artist ta " +
            "ON (a.id=ta.artist_id) WHERE track_id = ?";
    private static final String FIND_ALL_ARTISTS = "SELECT id, name, filename FROM artist";
    private static final String UPDATE_ARTIST = "UPDATE artist SET name = ?, filename = ? where id = ?";
    private static final String UPDATE_ARTIST_NAME = "UPDATE artist SET name = ? where id = ?";
    private static final String INSERT_ARTISTS_TO_TRACK = "INSERT into track_artist(track_id, artist_id) values (?, ?)";
    private static final String INSERT_ARTIST = "INSERT into artist(name, filename) values (?, ?)";
    private static final String DELETE_ARTISTS_TO_TRACK = "DELETE FROM track_artist WHERE track_id = ?";
    private static final String DELETE_ARTIST = "DELETE FROM artist WHERE id = ?";

    public ArtistDao(Connection connection, RowMapper<Artist> mapper) {
        super(connection, mapper);
    }

    @Override
    public List<Artist> getAll() throws DaoException {
        return executeQuery(FIND_ALL_ARTISTS);
    }

    public List<Artist> getByTrackId(Long id) throws DaoException {
        return executeQuery(FIND_ARTISTS_BY_TRACK_ID, id);
    }

//    public Optional<Artist> getArtistById(Long id) throws DaoException {
//        return executeForSingleResult(FIND_ARTIST_BY_ID, id);
//    }

    public void updateArtist(String newName, String filename, Long id) throws DaoException {
        executeUpdate(UPDATE_ARTIST, newName, filename, id);
    }

    public void updateArtistName(String newName, Long id) throws DaoException {
        executeUpdate(UPDATE_ARTIST_NAME, newName, id);
    }

    public void insertArtistsToTrack(Long newTrackId, Long artistId) throws DaoException {
        executeUpdate(INSERT_ARTISTS_TO_TRACK, newTrackId, artistId);
    }

    public void deleteArtistToTrack(Long trackId) throws DaoException {
        executeUpdate(DELETE_ARTISTS_TO_TRACK, trackId);
    }

    public void insertArtist(String artistName, String filename) throws DaoException {
        executeUpdate(INSERT_ARTIST, artistName, filename);
    }

    public Optional<Artist> findArtistByName(String name) throws DaoException {
        return executeForSingleResult(FIND_ARTIST_BY_NAME, name);
    }

    @Override
    public Optional<Artist> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_ARTIST_BY_ID, id);
    }

    @Override
    public void removeById(Long id) throws DaoException {
        executeUpdate(DELETE_ARTIST, id);
    }

    @Override
    protected String getTableName() {
        return Artist.TABLE;
    }
}
