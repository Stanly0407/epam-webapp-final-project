package com.epam.web.dao;

import com.epam.web.dto.MusicCollectionDto;
import com.epam.web.entities.MusicCollection;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.MusicCollectionDtoRowMapper;
import com.epam.web.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class MusicCollectionDao extends AbstractDao<MusicCollectionDto> implements Dao<MusicCollectionDto> {
    private static final Logger LOGGER = LogManager.getLogger(MusicCollectionDao.class);

    private static final String FIND_COLLECTION_BY_ID = "SELECT t.id, c.type, t.release_date, t.title, t.description, t.price, t.filename, a.id, a.name " +
            "FROM track t INNER JOIN artist a ON t.artist_id=a.id WHERE t.id=?"; //todo change

    private static final String ALBUM_SEARCH_CONDITION = "ALBUM";
    private static final String COLLECTION_SEARCH_CONDITION = "COLLECTION";

    private static final String GET_ALBUMS = "SELECT c.id, c.type, c.release_date, c.title, a.id, a.name \n" +
            "FROM collection c INNER JOIN artist a ON c.artist_id=a.id WHERE c.type = 'ALBUM'";
    private static final String GET_COLLECTIONS = "SELECT c.id, c.type, c.release_date, c.title FROM collection c WHERE c.type = 'COLLECTION'";
    private static final String QUERY_PART_FIVE_NEW_MUSIC_COLLECTIONS = " ORDER BY c.release_date DESC LIMIT 5";

    public MusicCollectionDao(Connection connection, RowMapper<MusicCollectionDto> mapper) {
        super(connection, mapper);
    }



    @Override
    public Optional<MusicCollectionDto> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_COLLECTION_BY_ID, new MusicCollectionDtoRowMapper(), id);
    }


    public List<MusicCollectionDto> getNewMusicCollections(String collectionType) throws DaoException {
        List<MusicCollectionDto> musicCollections = null;
        switch (collectionType) {
            case ALBUM_SEARCH_CONDITION:
                musicCollections = executeQuery(GET_ALBUMS + QUERY_PART_FIVE_NEW_MUSIC_COLLECTIONS, new MusicCollectionDtoRowMapper());
                break;
            case COLLECTION_SEARCH_CONDITION:
                musicCollections = executeQuery(GET_COLLECTIONS + QUERY_PART_FIVE_NEW_MUSIC_COLLECTIONS, new MusicCollectionDtoRowMapper());
                break;
        }
        return musicCollections;
    }


    @Override
    public void save(MusicCollectionDto entity) {

    }

    @Override
    public void removeById(Long id) {

    }

    @Override   // todo remove this method because all select queries are join queries
    protected String getTableName() {
        return MusicCollection.TABLE;
    }
}
