package com.epam.web.service;

import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.MusicCollectionDao;
import com.epam.web.dto.MusicCollectionDto;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;

import java.util.List;

public class MusicCollectionService {
    private static final String ALBUM_LIST = "ALBUM";
    private static final String PLAYLIST_LIST = "PLAYLIST";

    private DaoHelperFactory daoHelperFactory;

    public MusicCollectionService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }


    public List<MusicCollectionDto> getNewMusicCollections(String collectionType) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            List<MusicCollectionDto> musicCollections = null;
            switch (collectionType) {
                case ALBUM_LIST:
                    musicCollections = musicCollectionDao.getFiveNewAlbums();
                    break;
                case PLAYLIST_LIST:
                    musicCollections = musicCollectionDao.getFiveNewPlaylists();
                    break;
            }
            return musicCollections;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }



}
