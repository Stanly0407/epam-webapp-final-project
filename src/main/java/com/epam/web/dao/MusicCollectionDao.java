package com.epam.web.dao;

import com.epam.web.entities.MusicCollection;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.MusicCollectionRowMapper;
import com.epam.web.mapper.RowMapper;
import com.epam.web.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class MusicCollectionDao extends AbstractDao<MusicCollection> implements Dao<MusicCollection> {
    private static final Logger LOGGER = LogManager.getLogger(MusicCollectionDao.class);
    private static final String FIND_COLLECTION_BY_ID = "";

    private static final String FIND_ALBUM = "SELECT c.id, c.type, c.release_date, c.title, a.id, a.name FROM collection c " +
            "INNER JOIN artist a ON (a.id = c.artist_id) WHERE c.title = ? AND c.type = 'ALBUM'";
    private static final String FIND_PLAYLIST = "SELECT c.id, c.type, c.release_date, c.title FROM collection c WHERE c.title = ? AND c.type = 'PLAYLIST'";
    private static final String GET_ALBUMS = "SELECT c.id, c.release_date, c.title, c.type, a.id, a.name FROM collection c " +
            "INNER JOIN artist a ON (a.id = c.artist_id) WHERE c.type = 'ALBUM'";
    private static final String GET_PLAYLISTS = "SELECT c.id, c.release_date, c.title, c.type FROM collection c WHERE c.type = 'PLAYLIST'";
    private static final String QUERY_PART_FIVE_NEW_MUSIC_COLLECTIONS = " ORDER BY c.release_date DESC LIMIT 5";

    public MusicCollectionDao(Connection connection, RowMapper<MusicCollection> mapper) {
        super(connection, mapper);
    }

    @Override
    public Optional<MusicCollection> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_COLLECTION_BY_ID, new MusicCollectionRowMapper(), id);
    }

    public List<MusicCollection> findMusicByAlbumTitle(String searchSubject) throws DaoException {
        LOGGER.debug("findMusicByAlbumTitle = " + FIND_ALBUM);
        return executeQuery(FIND_ALBUM, searchSubject);
    }

    public List<MusicCollection> findMusicByPlaylistTitle(String searchSubject) throws DaoException {
        return executeQuery(FIND_PLAYLIST, searchSubject);
    }

    public List<MusicCollection> findFiveNewAlbums() throws DaoException {
        return executeQuery(GET_ALBUMS + QUERY_PART_FIVE_NEW_MUSIC_COLLECTIONS);
    }

    public List<MusicCollection> findFiveNewPlaylists() throws DaoException {
        return executeQuery(GET_PLAYLISTS + QUERY_PART_FIVE_NEW_MUSIC_COLLECTIONS);
    }

    @Override
    public void save(MusicCollection entity) {
    }

    @Override
    public void removeById(Long id) {
    }

    @Override   // todo remove this method because all select queries are join queries
    protected String getTableName() {
        return MusicCollection.TABLE;
    }
}
