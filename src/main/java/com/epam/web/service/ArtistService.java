package com.epam.web.service;

import com.epam.web.dao.ArtistDao;
import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.UserDao;
import com.epam.web.entities.Artist;
import com.epam.web.entities.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ArtistService {
    private static final Logger LOGGER = LogManager.getLogger(ArtistService.class);

    private DaoHelperFactory daoHelperFactory;

    public ArtistService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public List<Artist> getAllArtists() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            ArtistDao artistDao = daoHelper.createArtistDao();
            return artistDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
