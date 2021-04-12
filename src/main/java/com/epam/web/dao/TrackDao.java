package com.epam.web.dao;

import com.epam.web.entities.Track;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TrackDao extends AbstractDao<Track> implements Dao<Track> {

    private static final String UPDATE_TRACK = "UPDATE track SET t.release_date=?, title=?, price=? where id=?";
    private static final String INSERT_TRACK = "INSERT INTO track (release_date, title, price) VALUE (?, ?, ?)";
    private static final String GET_TRACK_LIST = "SELECT t.id, t.release_date, t.title, t.price FROM track t";
    private static final String FIND_TRACK_BY_ID = "SELECT t.id, t.release_date, t.title, t.price FROM track t WHERE t.id=?";
    private static final String FIND_TRACKS_BY_TRACK = "SELECT t.id, t.release_date, t.title, t.price FROM track t WHERE t.title=?";
    private static final String FIND_TRACKS_BY_ARTIST = "SELECT t.id, t.release_date, t.title, t.price a.id, a.name FROM track t " +
            "INNER JOIN track_artist ta ON (t.id = ta.track_id) INNER JOIN artist a ON (ta.artist_id=a.id) WHERE a.name = ?";
    private static final String FIND_FIVE_NEW_TRACKS = "SELECT t.id, t.release_date, t.title, t.price FROM track t " +
            "ORDER BY t.release_date DESC LIMIT 5";

    public TrackDao(Connection connection, RowMapper<Track> mapper) {
        super(connection, mapper);
    }

    public void editTrack(String releaseDate, String title, String price, String id) throws DaoException {
        executeUpdate(UPDATE_TRACK, releaseDate, title, price, id);
    }

    public List<Track> findMusicByTrack(String searchSubject) throws DaoException {
        return executeQuery(FIND_TRACKS_BY_TRACK, searchSubject);
    }

    public List<Track> findMusicByArtist(String searchSubject) throws DaoException {
        return executeQuery(FIND_TRACKS_BY_ARTIST, searchSubject);
    }

    @Override
    public Optional<Track> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_TRACK_BY_ID, id);
    }

    public List<Track> getAllTracks() throws DaoException {
        return executeQuery(GET_TRACK_LIST);
    }

    public List<Track> getNewTracks() throws DaoException {
        return executeQuery(FIND_FIVE_NEW_TRACKS);
    }

    @Override
    public void save(Track entity) {
    }

    @Override
    public void removeById(Long id) {
    }

    @Override   // todo: remove
    protected String getTableName() {
        return Track.TABLE;
    }
}
