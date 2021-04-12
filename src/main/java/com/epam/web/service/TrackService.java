package com.epam.web.service;

import com.epam.web.dao.*;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Artist;
import com.epam.web.entities.Order;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrackService {
    private static final Logger LOGGER = LogManager.getLogger(TrackService.class);

    private static final String TRACK_SEARCH_CONDITION = "Track";
    private static final String ARTIST_SEARCH_CONDITION = "Artist";

    private DaoHelperFactory daoHelperFactory;

    public TrackService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public void editTrack(String releaseDate, String title, String price, String id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            trackDao.editTrack(releaseDate, title, price, id);
            // todo будет включать и обновление артиста админом, т.е. в дао будет транзакция из двух запросов update
        } catch (DaoException e) {
            LOGGER.debug("createTrack " + e);
            throw new ServiceException(e);
        }
    }

    public List<Track> getMusicByCondition(String searchSubject, String searchCondition) throws ServiceException {
        LOGGER.debug("Called method getAllTracks");
        try (DaoHelper daoHelper = daoHelperFactory.create()) {  //todo part in common method
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> searchedTracks = null;
            switch (searchCondition) {
                case TRACK_SEARCH_CONDITION:
                    searchedTracks = trackDao.findMusicByTrack(searchSubject);
                    break;
                case ARTIST_SEARCH_CONDITION:
                    searchedTracks = trackDao.findMusicByArtist(searchSubject);
                    break;
            }
            return searchedTracks;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Track> getAllTracks() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {  //todo part in common method
            TrackDao trackDao = daoHelper.createTrackDao();
            return trackDao.getAllTracks();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<Track> getTrack(Long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            return trackDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<TrackDto> getNewTracks(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {  //todo part in common method
            TrackDao trackDao = daoHelper.createTrackDao();
            ArtistDao artistDao = daoHelper.createArtistDao();
            OrderDao orderDao = daoHelper.createOrderDao();
            List<Track> tracks = trackDao.getNewTracks();
            List<TrackDto> trackDtoList = new ArrayList<>();
            for (Track track : tracks) {
                Long trackId = track.getId();
                List<Artist> trackArtists = artistDao.getByTrackId(trackId);
                Optional<Order> optionalOrder = orderDao.isPaidTrackByCurrentUser(userId, trackId);
                boolean isPaid;
                if(optionalOrder.isPresent()){
                Order order = optionalOrder.get();
                    isPaid = order.isPaid();
                } else {
                    isPaid = false;
                }
                String title = track.getTitle();
                BigDecimal price = track.getPrice();
                TrackDto trackDto = new TrackDto.Builder()
                        .id(trackId)
                        .title(title)
                        .price(price)
                        .artists(trackArtists)
                        .isPaid(isPaid)
                        .build();
                trackDtoList.add(trackDto);
            }
            return trackDtoList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
