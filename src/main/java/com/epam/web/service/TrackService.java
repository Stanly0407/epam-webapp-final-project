package com.epam.web.service;

import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.TrackDao;
import com.epam.web.entities.Artist;
import com.epam.web.entities.Track;
import com.epam.web.entities.TrackAndArtist;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class TrackService {
    private static final Logger LOGGER = LogManager.getLogger(TrackService.class);

    private DaoHelperFactory daoHelperFactory;

    public TrackService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }


    public void createTrack(String title, String description, String price, String filename, String artistId) throws ServiceException {
        LOGGER.debug("Called method createTrack");
        // logic for changing price and artistId data type (after validation!)
        BigDecimal priceBigDecimal = new BigDecimal(price);
        Long artistIdLong = Long.valueOf(artistId);
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            trackDao.createTrack(title, description, priceBigDecimal, filename, artistIdLong);
        } catch (DaoException | IOException | InterruptedException e) {
            throw new ServiceException((DaoException) e);
        }
    }

    public List<Track> getAllTracks() throws ServiceException {
        LOGGER.debug("Called method getAllTracks");
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
           // return trackDao.getTrackList(Track.TABLE, Artist.TABLE);
            return trackDao.getAll();

        } catch (DaoException | IOException | InterruptedException e) {
            throw new ServiceException((DaoException) e);
        }
    }

}
