package com.epam.web.service;

import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.MusicCollectionDao;
import com.epam.web.dto.MusicCollectionDto;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;

import java.util.List;

public class MusicCollectionService {

    private DaoHelperFactory daoHelperFactory;

    public MusicCollectionService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }


    public List<MusicCollectionDto> getNewMusicCollections(String collectionType) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {  //todo part in common method
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            return musicCollectionDao.getNewMusicCollections(collectionType);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


}
