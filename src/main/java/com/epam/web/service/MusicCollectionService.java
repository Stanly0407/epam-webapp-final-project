package com.epam.web.service;

import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.MusicCollectionDao;
import com.epam.web.entities.MusicCollection;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;

import java.util.List;

public class MusicCollectionService {
    private static final String ALBUM_LIST = "ALBUM";
    private static final String PLAYLIST_LIST = "PLAYLIST";
    private static final String ALBUM_SEARCH_CONDITION = "Album";
    private static final String PLAYLIST_SEARCH_CONDITION = "Playlist";

    private DaoHelperFactory daoHelperFactory;

    public MusicCollectionService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public List<MusicCollection> getAllAlbums() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            return musicCollectionDao.getAllAlbums();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<MusicCollection> getAllPlaylists() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            return musicCollectionDao.getAllPlaylists();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<MusicCollection> getNewMusicCollections(String collectionType) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            List<MusicCollection> musicCollections = null;
            switch (collectionType) {
                case ALBUM_LIST:
                    musicCollections = musicCollectionDao.findFiveNewAlbums();
                    break;
                case PLAYLIST_LIST:
                    musicCollections = musicCollectionDao.findFiveNewPlaylists();
                    break;
            }
            return musicCollections;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<MusicCollection> getMusicByCondition(String searchSubject, String searchCondition) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            List<MusicCollection> searchedTracks = null;
            switch (searchCondition) {
                case ALBUM_SEARCH_CONDITION:
                    searchedTracks = musicCollectionDao.findMusicByAlbumTitle(searchSubject);
                    break;
                case PLAYLIST_SEARCH_CONDITION:
                    searchedTracks = musicCollectionDao.findMusicByPlaylistTitle(searchSubject);
                    break;
            }
            return searchedTracks;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }



}
