package com.epam.web.service;

import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.MusicCollectionDao;
import com.epam.web.dao.TrackDao;
import com.epam.web.dto.MusicCollectionDto;
import com.epam.web.dto.TrackDto;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MusicCollectionService {
    private static final Logger LOGGER = LogManager.getLogger(MusicCollectionService.class);

    private DaoHelperFactory daoHelperFactory;
    public MusicCollectionService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }


    public List<MusicCollectionDto> getNewMusicCollections(String collectionType) throws ServiceException {
        LOGGER.debug("Called method getAllTracks");
        try (DaoHelper daoHelper = daoHelperFactory.create()) {  //todo part in common method
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            return musicCollectionDao.getNewMusicCollections(collectionType);

        } catch (DaoException | InterruptedException e) {
            throw new ServiceException((DaoException) e);
        }
    }



}
