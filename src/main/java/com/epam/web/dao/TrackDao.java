package com.epam.web.dao;

import com.epam.web.entities.Track;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TrackDao extends AbstractDao<Track> implements Dao<Track> {
    private static final String DELETE_TRACK = "DELETE FROM track WHERE id = ?";
    private static final String INSERT_TRACK = "INSERT into track(id, release_date, title, price, filename) values (null, ?, ?, ?, ?)";
    private static final String UPDATE_TRACK = "UPDATE track SET release_date=?, title=?, price=?, filename=? where id=?";
    private static final String UPDATE_TRACK_INFO = "UPDATE track SET release_date=?, title=?, price=? where id=?";
    private static final String GET_TRACK_LIST = "SELECT id, release_date, title, price, filename FROM track";
    private static final String GET_TRACK_LIST_PAGE = "SELECT id, release_date, title, price, filename FROM track LIMIT ? OFFSET ?";
    private static final String FIND_TRACK_BY_ID = "SELECT id, release_date, title, price, filename FROM track WHERE id=?";
    private static final String FIND_TRACKS_BY_TITLE = "SELECT id, release_date, title, price, filename FROM track WHERE title = ?";
    private static final String FIND_TRACKS_BY_ARTIST = "SELECT t.id, t.release_date, t.title, t.price, t.filename, a.id, a.name, a.filename FROM track t " +
            "INNER JOIN track_artist ta ON (t.id = ta.track_id) INNER JOIN artist a ON (ta.artist_id=a.id) WHERE a.name = ?";
    private static final String FIND_FIVE_NEW_TRACKS = "SELECT id, release_date, title, price, filename  FROM track t ORDER BY t.release_date DESC LIMIT 5";
    private static final String FIND_ORDERED_TRACKS = "SELECT t.id, t.release_date, t.title, t.price, filename FROM track t " +
            "INNER JOIN purchase_order_track p ON (p.track_id=t.id) INNER JOIN purchase_order po ON (po.id=p.order_id) " +
            "WHERE po.user_id = ? AND po.is_paid = false";
    private static final String FIND_TRACK_IN_UNPAID_ORDER = "SELECT t.id, t.release_date, t.title, t.price, filename  FROM track t " +
            "INNER JOIN purchase_order_track p ON (p.track_id=t.id) INNER JOIN purchase_order po ON (po.id=p.order_id) " +
            "WHERE po.user_id = ? AND po.is_paid = false AND t.id = ?";
    private static final String FIND_ALL_PAID_TRACKS_BY_USER_ID = "SELECT t.id, t.release_date, t.title, t.price, filename  FROM track t " +
            "INNER JOIN purchase_order_track p ON (p.track_id=t.id) INNER JOIN purchase_order po ON (po.id=p.order_id) " +
            "WHERE po.user_id = ? AND po.is_paid = true";
    private static final String FIND_PAID_TRACKS_BY_ORDER_ID = "SELECT t.id, t.release_date, t.title, t.price, t.filename  FROM track t " +
            "INNER JOIN purchase_order_track p ON (p.track_id=t.id) INNER JOIN purchase_order po ON (po.id=p.order_id) " +
            "WHERE po.id = ?";
    private static final String FIND_ALL_ARTIST_TRACKS = "SELECT t.id, t.release_date, t.title, t.price, t.filename, a.id, a.name, a.filename " +
            "FROM track t INNER JOIN track_artist ta ON (t.id = ta.track_id) INNER JOIN artist a ON (ta.artist_id=a.id) WHERE a.id = ?";
    private static final String FIND_COLLECTION_TRACKS = "SELECT t.id, t.release_date, t.title, t.price, t.filename, a.id, a.name, a.filename FROM track t " +
            "INNER JOIN track_artist ta ON (t.id = ta.track_id) INNER JOIN artist a ON (ta.artist_id=a.id) INNER JOIN track_collection tc " +
            "ON (t.id=tc.track_id) INNER JOIN collection c ON (c.id=tc.collection_id) WHERE c.id = ?";
    private static final String FIND_TRACK_BY_PARAMETERS = "SELECT id, release_date, title, price, filename FROM track " +
            "WHERE release_date=? AND title=? AND price=? AND filename=?";

    public TrackDao(Connection connection, RowMapper<Track> mapper) {
        super(connection, mapper);
    }

    public void insertTrack(String releaseDate, String title, String price, String filename) throws DaoException {
        executeUpdate(INSERT_TRACK, releaseDate, title, price, filename);
    }

    public Optional<Track> getTrackByParameters(String releaseDate, String title, String price, String filename) throws DaoException {
        return executeForSingleResult(FIND_TRACK_BY_PARAMETERS, releaseDate, title, price, filename);
    }

    public void editTrack(String releaseDate, String title, String price, String filename, Long id) throws DaoException {
        executeUpdate(UPDATE_TRACK, releaseDate, title, price, filename, id);
    }

    public void editTrackInfo(String releaseDate, String title, String price,  Long id) throws DaoException {
        executeUpdate(UPDATE_TRACK_INFO, releaseDate, title, price, id);
    }

    public List<Track> findMusicByTrack(String searchSubject) throws DaoException {
        return executeQuery(FIND_TRACKS_BY_TITLE, searchSubject);
    }

    public List<Track> findMusicByArtist(String searchSubject) throws DaoException {
        return executeQuery(FIND_TRACKS_BY_ARTIST, searchSubject);
    }

    public List<Track> findOrderedTracks(Long userId) throws DaoException {
        return executeQuery(FIND_ORDERED_TRACKS, userId);
    }

    public Optional<Track> getTrackFromCart(Long userId, Long trackId) throws DaoException {
        return executeForSingleResult(FIND_TRACK_IN_UNPAID_ORDER, userId, trackId);
    }

    public List<Track> findAllPaidTracks(Long userId) throws DaoException {
        return executeQuery(FIND_ALL_PAID_TRACKS_BY_USER_ID, userId);
    }

    public List<Track> findPaidTracksByOrderId(Long orderId) throws DaoException {
        return executeQuery(FIND_PAID_TRACKS_BY_ORDER_ID, orderId);
    }

    public List<Track> findArtistMusic(Long artistId) throws DaoException {
        return executeQuery(FIND_ALL_ARTIST_TRACKS, artistId);
    }
    public List<Track> findCollectionMusic(Long collectionId) throws DaoException {
        return executeQuery(FIND_COLLECTION_TRACKS, collectionId);
    }


    @Override
    public Optional<Track> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_TRACK_BY_ID, id);
    }

    public List<Track> getAllTracks() throws DaoException {
        return executeQuery(GET_TRACK_LIST);
    }

    public List<Track> getTracksPage(int limit, int offset) throws DaoException {
        return executeQuery(GET_TRACK_LIST_PAGE, limit, offset);
    }

    public List<Track> getNewTracks() throws DaoException {
        return executeQuery(FIND_FIVE_NEW_TRACKS);
    }

    @Override
    public void save(Track entity) throws DaoException{
    }

    @Override
    public void removeById(Long id) throws DaoException{
    }

    @Override
    protected String getTableName() {
        return Track.TABLE;
    }
}
