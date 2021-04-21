package com.epam.web.dao;

import com.epam.web.entities.Track;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TrackDao extends AbstractDao<Track> implements Dao<Track> {

    private static final String UPDATE_TRACK = "UPDATE track SET t.release_date=?, title=?, price=?, filename=? where id=?";
    private static final String GET_TRACK_LIST = "SELECT t.id, t.release_date, t.title, t.price, filename FROM track t";
    private static final String FIND_TRACK_BY_ID = "SELECT t.id, t.release_date, t.title, t.price, filename FROM track t WHERE t.id=?";
    private static final String FIND_TRACKS_BY_TITLE = "SELECT t.id, t.release_date, t.title, t.price, filename FROM track t WHERE t.title=?";
    private static final String FIND_TRACKS_BY_ARTIST = "SELECT t.id, t.release_date, t.title, t.price, filename, a.id, a.name FROM track t " +
            "INNER JOIN track_artist ta ON (t.id = ta.track_id) INNER JOIN artist a ON (ta.artist_id=a.id) WHERE a.name = ?";
    private static final String FIND_FIVE_NEW_TRACKS = "SELECT t.id, t.release_date, t.title, t.price, filename  FROM track t " +
            "ORDER BY t.release_date DESC LIMIT 5";
    private static final String FIND_ORDERED_TRACKS = "SELECT t.id, t.release_date, t.title, t.price, filename FROM track t " +
            "INNER JOIN purchase_order_track p ON (p.track_id=t.id) INNER JOIN purchase_order po ON (po.id=p.order_id) " +
            "WHERE po.user_id = ? AND po.is_paid = false";
    private static final String FIND_TRACK_IN_UNPAID_ORDER = "SELECT t.id, t.release_date, t.title, t.price, filename  FROM track t " +
            "INNER JOIN purchase_order_track p ON (p.track_id=t.id) INNER JOIN purchase_order po ON (po.id=p.order_id) " +
            "WHERE po.user_id = ? AND po.is_paid = false AND t.id = ?";
    private static final String FIND_ALL_PAID_TRACKS_BY_USER_ID = "SELECT t.id, t.release_date, t.title, t.price, filename  FROM track t " +
            "INNER JOIN purchase_order_track p ON (p.track_id=t.id) INNER JOIN purchase_order po ON (po.id=p.order_id) " +
            "WHERE po.user_id = ? AND po.is_paid = true";
    private static final String FIND_PAID_TRACKS_BY_ORDER_ID = "SELECT t.id, t.release_date, t.title, t.price, filename  FROM track t " +
            "INNER JOIN purchase_order_track p ON (p.track_id=t.id) INNER JOIN purchase_order po ON (po.id=p.order_id) " +
            "WHERE po.id = ?";
    private static final String FIND_ALL_ARTIST_TRACKS = "SELECT t.id, t.release_date, t.title, t.price, filename, a.id, a.name " +
            "FROM track t INNER JOIN track_artist ta ON (t.id = ta.track_id) INNER JOIN artist a ON (ta.artist_id=a.id) WHERE a.id = ?";
    private static final String FIND_COLLECTION_TRACKS = "SELECT t.id, t.release_date, t.title, t.price, filename, a.id, a.name FROM track t " +
            "INNER JOIN track_artist ta ON (t.id = ta.track_id) INNER JOIN artist a ON (ta.artist_id=a.id) INNER JOIN track_collection tc " +
            "ON (t.id=tc.track_id) INNER JOIN collection c ON (c.id=tc.collection_id) WHERE c.id = ?";

    public TrackDao(Connection connection, RowMapper<Track> mapper) {
        super(connection, mapper);
    }

    public void editTrack(String releaseDate, String title, String price, String id) throws DaoException {
        executeUpdate(UPDATE_TRACK, releaseDate, title, price, id);
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

    public List<Track> getNewTracks() throws DaoException {
        return executeQuery(FIND_FIVE_NEW_TRACKS);
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
