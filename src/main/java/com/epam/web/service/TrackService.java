package com.epam.web.service;

import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.TrackDao;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Artist;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TrackService {
    private static final Logger LOGGER = LogManager.getLogger(TrackService.class);

    private DaoHelperFactory daoHelperFactory;

    public TrackService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }


    public void editTrack(String title, String description, String price, String filename, String id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            trackDao.editTrack(title, description, price, filename, id);
            // todo будет включать и обновление артиста админом, т.е. в дао будет транзакция из двух запросов update
        } catch (DaoException | InterruptedException e) {
            LOGGER.debug("createTrack " + e);
            throw new ServiceException((DaoException) e);
        }
    }

    public List<TrackDto> getMusicByCondition() throws ServiceException {
        LOGGER.debug("Called method getAllTracks");
        try (DaoHelper daoHelper = daoHelperFactory.create()) {  //todo part in common method
            TrackDao trackDao = daoHelper.createTrackDao();
            // return trackDao.getTrackList(Track.TABLE, Artist.TABLE);
            return trackDao.findMusicByCondition();

        } catch (DaoException | InterruptedException e) {
            throw new ServiceException((DaoException) e);
        }
    }

    public List<TrackDto> getAllTracks() throws ServiceException {
        LOGGER.debug("Called method getAllTracks");
        try (DaoHelper daoHelper = daoHelperFactory.create()) {  //todo part in common method
          TrackDao trackDao = daoHelper.createTrackDao();
             return trackDao.getAllTracks();

        } catch (DaoException | InterruptedException e) {
            throw new ServiceException((DaoException) e);
        }
    }

    public Optional<TrackDto> getTrack(Long id) throws ServiceException {
        LOGGER.debug("Called method getAllTracks");
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            return trackDao.getById(id);

        } catch (DaoException | InterruptedException e) {
            throw new ServiceException((DaoException) e);
        }
    }

}
