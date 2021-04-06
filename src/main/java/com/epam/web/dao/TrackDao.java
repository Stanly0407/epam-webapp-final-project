package com.epam.web.dao;

import com.epam.web.dto.TrackDto;
import com.epam.web.entities.MusicCollection;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;
import com.epam.web.mapper.TrackDtoRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TrackDao extends AbstractDao<TrackDto> implements Dao<TrackDto> {
    private static final Logger LOGGER = LogManager.getLogger(TrackDao.class);

    private static final String UPDATE_TRACK = "UPDATE track SET title=?, description=?, price=?, filename=? where id=?";

    private static final String INSERT_TRACK = "INSERT INTO track (id, title, description, price, filename, artist_id) VALUE (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_TRACK_LIST = "SELECT t.id, t.release_date, t.title, t.description, t.price, t.filename, a.id, " +
            "a.name, a.lastname FROM track t INNER JOIN artist a ON (t.artist_id = a.id)";

    private static final String FIND_TRACK_BY_ID = "SELECT t.id, t.release_date, t.title, t.description, t.price, t.filename, a.id, a.name, a.lastname " +
            "FROM track t INNER JOIN artist a ON t.artist_id=a.id WHERE t.id=?";

    private static final String TRACK_SEARCH_CONDITION = "Track";
    private static final String ARTIST_SEARCH_CONDITION = "Artist";
    private static final String ALBUM_SEARCH_CONDITION = "Album";
    private static final String COLLECTION_SEARCH_CONDITION = "Collection";

    private static final String FIND_TRACKS_BY_TITLE = "SELECT t.id, t.release_date, t.title, t.description, t.price, t.filename, a.id, a.name " +
            "FROM track t INNER JOIN artist a ON t.artist_id=a.id WHERE t.title=?";
    private static final String FIND_TRACKS_BY_ARTIST_NAME = "SELECT t.id, t.release_date, t.title, t.description, t.price, t.filename, a.id, a.name " +
            "FROM track t INNER JOIN artist a ON t.artist_id=a.id WHERE a.=?";
    private static final String FIND_TRACKS_BY_ALBUM_TITLE = "SELECT c.title, t.id, t.release_date,t.title, t.description, t.price, t.filename, t.artist_id, \n" +
            "a.name FROM track t INNER JOIN artist a ON t.artist_id=a.id INNER JOIN track_collection tc ON t.id=tc.track_id \n" +
            "INNER JOIN collection c ON tc.collection_id=c.id where c.title = ?  AND c.type = 'ALBUM'";
    private static final String FIND_TRACKS_BY_COLLECTION_TITLE = "SELECT c.title, t.id, t.release_date, t.title, t.description, t.price, t.filename, t.artist_id, \n" +
            "a.name FROM track t INNER JOIN artist a ON t.artist_id=a.id INNER JOIN track_collection tc ON t.id=tc.track_id \n" +
            "INNER JOIN collection c ON tc.collection_id=c.id where c.title = ?  AND c.type = 'COLLECTION'";

    private static final String GET_FIVE_NEW_TRACKS = "SELECT t.id, t.release_date, t.title, t.description, t.price, t.filename, t.artist_id, a.name " +
            "FROM track t INNER JOIN artist a ON t.artist_id=a.id ORDER BY t.release_date DESC LIMIT 5";


    public TrackDao(Connection connection, RowMapper<TrackDto> mapper) {
        super(connection, mapper);
    }

    public void editTrack(String title, String description, String price, String filename, String id) throws DaoException {
        executeUpdate(UPDATE_TRACK, title, description, price, filename, id);
    }

    public List<TrackDto> findMusicByCondition(String searchSubject, String searchCondition) throws DaoException {
        LOGGER.debug("method - findMusicByCondition: searchSubject = " + searchSubject + ", searchCondition = " + searchCondition);
        String query = null;
        switch (searchCondition) {
            case TRACK_SEARCH_CONDITION:
                query = FIND_TRACKS_BY_TITLE;
                break;
            case ARTIST_SEARCH_CONDITION:
                query = FIND_TRACKS_BY_ARTIST_NAME;
                break;
            case ALBUM_SEARCH_CONDITION:
                query = FIND_TRACKS_BY_ALBUM_TITLE;
                break;
            case COLLECTION_SEARCH_CONDITION:
                query = FIND_TRACKS_BY_COLLECTION_TITLE;
        }
        return executeQuery(query, new TrackDtoRowMapper(), searchSubject);
    }

    @Override
    public Optional<TrackDto> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_TRACK_BY_ID, new TrackDtoRowMapper(), id);
    }

    public List<TrackDto> getAllTracks() throws DaoException {
        return executeQuery(GET_TRACK_LIST, new TrackDtoRowMapper());
    }

    public List<TrackDto> getNewTracks() throws DaoException {
        return executeQuery(GET_FIVE_NEW_TRACKS, new TrackDtoRowMapper());
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
