package com.epam.web.service;

import com.epam.web.dao.ArtistDao;
import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.entities.Artist;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public class ArtistService {

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

    public void addArtist(String artistId, String artistName, String filename) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            ArtistDao artistDao = daoHelper.createArtistDao();
            Long id;
            if (artistId != null) {
                id = Long.valueOf(artistId);
                if (filename == null) {
                    artistDao.updateArtistName(artistName, id);
                } else {
                    artistDao.updateArtist(artistName, filename, id);
                }
            } else {
                artistDao.insertArtist(artistName, filename);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Artist getArtistById(Long artistId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            ArtistDao artistDao = daoHelper.createArtistDao();
            Optional<Artist> artist = artistDao.getById(artistId);
            return artist.orElse(null);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
