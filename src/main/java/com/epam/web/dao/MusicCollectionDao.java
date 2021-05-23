package com.epam.web.dao;

import com.epam.web.entities.MusicCollection;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class MusicCollectionDao extends AbstractDao<MusicCollection> implements Dao<MusicCollection> {

    private static final String FIND_ALBUM_BY_ID = "SELECT c.id, c.type, c.release_date, c.title, c.filename, a.id, a.name, a.filename FROM collection c " +
            "INNER JOIN artist a ON (a.id = c.artist_id) WHERE c.id = ?";
    private static final String FIND_PLAYLIST_BY_ID = "SELECT c.id, c.type, c.release_date, c.title, c.filename FROM collection c " +
            " WHERE c.id = ?";
    private static final String FIND_ALBUM = "SELECT c.id, c.type, c.release_date, c.title, c.filename, a.id, a.name, a.filename FROM collection c " +
            "INNER JOIN artist a ON (a.id = c.artist_id) WHERE c.title = ? AND c.type = 'ALBUM'";
    private static final String FIND_PLAYLIST = "SELECT c.id, c.type, c.release_date, c.title, c.filename FROM collection c WHERE c.title = ? AND c.type = 'PLAYLIST'";
    private static final String GET_ALBUMS = "SELECT c.id, c.release_date, c.title, c.filename, c.type, a.id, a.name, a.filename FROM collection c " +
            "INNER JOIN artist a ON (a.id = c.artist_id) WHERE c.type = 'ALBUM'";
    private static final String GET_PLAYLISTS = "SELECT c.id, c.release_date, c.title, c.filename, c.type FROM collection c WHERE c.type = 'PLAYLIST'";
    private static final String QUERY_PART_FIVE_NEW_MUSIC_COLLECTIONS = " ORDER BY c.release_date DESC LIMIT 5";
    private static final String INSERT_PLAYLIST = "INSERT INTO collection (release_date, title, filename, type) value (?, ?, ?, 'PLAYLIST')";
    private static final String INSERT_ALBUM = "INSERT INTO collection (release_date, title, filename, artist_id, type) value (?, ?, ?, ?, 'ALBUM')";
    private static final String GET_ALBUMS_LIST_PAGE = "SELECT c.id, c.release_date, c.title, c.filename, c.type, a.id, a.name, a.filename " +
            "FROM collection c INNER JOIN artist a ON (a.id = c.artist_id) WHERE c.type = 'ALBUM' LIMIT ? OFFSET ?";
    private static final String GET_PLAYLISTS_LIST_PAGE = "SELECT c.id, c.release_date, c.title, c.filename, c.type FROM collection c " +
            "WHERE c.type = 'PLAYLIST' LIMIT ? OFFSET ?";
    private static final String DELETE_COLLECTION_TRACK = "DELETE FROM track_collection WHERE track_id = ? AND collection_id = ?";
    private static final String UPDATE_ALBUM = "UPDATE collection SET release_date = ?, title = ?, filename = ?, artist_id = ? where id = ?";
    private static final String UPDATE_ALBUM_INFO = "UPDATE collection SET release_date = ?, title = ?, artist_id = ? where id = ?";
    private static final String UPDATE_PLAYLIST = "UPDATE collection SET release_date = ?, title = ?, filename = ? where id = ?";
    private static final String UPDATE_PLAYLIST_INFO = "UPDATE collection SET release_date = ?, title = ? where id = ?";
    private static final String INSERT_TRACK_TO_COLLECTION = "INSERT INTO track_collection (track_id, collection_id) value (?, ?)";
    private static final String FIND_ALBUMS_BY_ARTISTS_ID = "SELECT c.id, c.type, c.release_date, c.title, c.filename, a.id, a.name, a.filename FROM collection c " +
            "INNER JOIN artist a ON (a.id = c.artist_id) WHERE c.artist_id = ? AND c.type = 'ALBUM'";
    private static final String FIND_ALBUM_TRACK_COLLECTION = "SELECT c.id, c.type, c.release_date, c.title, c.filename, a.id, a.name, a.filename FROM collection c " +
            "INNER JOIN artist a ON (a.id = c.artist_id) INNER JOIN track_collection tc ON (c.id = tc.collection_id) " +
            "WHERE tc.collection_id = ? AND tc.track_id = ?";
    private static final String FIND_PLAYLIST_TRACK_COLLECTION = "SELECT c.id, c.type, c.release_date, c.title, c.filename from collection c " +
            "INNER JOIN track_collection tc ON (c.id = tc.collection_id) WHERE tc.collection_id = ? AND tc.track_id = ?";
    private static final String DELETE_COLLECTION_BY_ID = "DELETE FROM collection WHERE id = ?";

    public MusicCollectionDao(Connection connection, RowMapper<MusicCollection> mapper) {
        super(connection, mapper);
    }

    public Optional<MusicCollection> getAlbumById(Long id) throws DaoException {
        return executeForSingleResult(FIND_ALBUM_BY_ID, id);
    }

    public Optional<MusicCollection> getPlaylistById(Long id) throws DaoException {
        return executeForSingleResult(FIND_PLAYLIST_BY_ID, id);
    }

    public List<MusicCollection> findMusicByAlbumTitle(String searchSubject) throws DaoException {
        return executeQuery(FIND_ALBUM, searchSubject);
    }

    public Optional<MusicCollection> findAlbumTrack(Long albumId, Long trackId) throws DaoException {
        return executeForSingleResult(FIND_ALBUM_TRACK_COLLECTION, albumId, trackId);
    }

    public Optional<MusicCollection> findPlaylistTrack(Long playlistId, Long trackId) throws DaoException {
        return executeForSingleResult(FIND_PLAYLIST_TRACK_COLLECTION, playlistId, trackId);
    }

    public List<MusicCollection> findMusicByPlaylistTitle(String searchSubject) throws DaoException {
        return executeQuery(FIND_PLAYLIST, searchSubject);
    }

    public List<MusicCollection> getAllAlbums() throws DaoException {
        return executeQuery(GET_ALBUMS);
    }

    public List<MusicCollection> getAllPlaylists() throws DaoException {
        return executeQuery(GET_PLAYLISTS);
    }

    public List<MusicCollection> findFiveNewAlbums() throws DaoException {
        return executeQuery(GET_ALBUMS + QUERY_PART_FIVE_NEW_MUSIC_COLLECTIONS);
    }

    public List<MusicCollection> findFiveNewPlaylists() throws DaoException {
        return executeQuery(GET_PLAYLISTS + QUERY_PART_FIVE_NEW_MUSIC_COLLECTIONS);
    }

    public List<MusicCollection> getArtistAlbums(Long artistId) throws DaoException {
        return executeQuery(FIND_ALBUMS_BY_ARTISTS_ID, artistId);
    }

    public void insertAlbum(String releaseDate, String title, String filename, String artistId) throws DaoException {
        executeUpdate(INSERT_ALBUM, releaseDate, title, filename, artistId);
    }

    public void insertPlaylist(String releaseDate, String title, String filename) throws DaoException {
        executeUpdate(INSERT_PLAYLIST, releaseDate, title, filename);
    }

    public void deleteTrackFromCollection(Long trackId, Long collectionId) throws DaoException {
        executeUpdate(DELETE_COLLECTION_TRACK, trackId, collectionId);
    }

    public void updateAlbum(Long albumId, String releaseDate, String albumTitle, String filename, String artistId) throws DaoException {
        executeUpdate(UPDATE_ALBUM, releaseDate, albumTitle, filename, artistId, albumId);
    }

    public void updateAlbumInfo(Long albumId, String releaseDate, String albumTitle, String artistId) throws DaoException {
        executeUpdate(UPDATE_ALBUM_INFO, releaseDate, albumTitle, artistId, albumId);
    }

    public void updatePlaylist(Long playlistId, String releaseDate, String playlistTitle, String filename) throws DaoException {
        executeUpdate(UPDATE_PLAYLIST, releaseDate, playlistTitle, filename, playlistId);
    }

    public void updatePlaylistInfo(Long playlistId, String releaseDate, String playlistTitle) throws DaoException {
        executeUpdate(UPDATE_PLAYLIST_INFO, releaseDate, playlistTitle, playlistId);
    }

    public void insertTrackToCollection(Long trackId, Long albumId) throws DaoException {
        executeUpdate(INSERT_TRACK_TO_COLLECTION, trackId, albumId);
    }

    @Override
    public Optional<MusicCollection> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_ALBUM_BY_ID, id);
    }

    @Override
    public void removeById(Long id) throws DaoException {
        executeUpdate(DELETE_COLLECTION_BY_ID, id);
    }

    @Override
    protected String getTableName() {
        return MusicCollection.TABLE;
    }
}
