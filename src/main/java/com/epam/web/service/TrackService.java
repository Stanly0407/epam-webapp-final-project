package com.epam.web.service;

import com.epam.web.dao.*;
import com.epam.web.dto.TrackDto;
import com.epam.web.dto.TrackStatusEnum;
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

    public List<TrackDto> getMusicByCondition(String searchSubject, String searchCondition, Long userId) throws ServiceException {
        LOGGER.debug("Called method getAllTracks");
        try (DaoHelper daoHelper = daoHelperFactory.create()) {  //todo part in common method
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> searchedTracks = new ArrayList<>();
            switch (searchCondition) {
                case TRACK_SEARCH_CONDITION:
                    searchedTracks = trackDao.findMusicByTrack(searchSubject);
                    break;
                case ARTIST_SEARCH_CONDITION:
                    searchedTracks = trackDao.findMusicByArtist(searchSubject);
                    break;
            }
            if (searchedTracks != null) {
                return createTrackDtoList(searchedTracks, daoHelper, userId);
            } else {
                return new ArrayList<>();
            }
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
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> tracks = trackDao.getNewTracks();
            return createTrackDtoList(tracks, daoHelper, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<TrackDto> getOrderedTracks(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> orderedTracks = trackDao.findOrderedTracks(userId);
            if (orderedTracks != null) {
                return createTrackDtoList(orderedTracks, daoHelper, userId);
            } else {
                return new ArrayList<>();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<TrackDto> getPurchasedTracks(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> purchasedTracks = trackDao.findPaidTracks(userId);
            if (!purchasedTracks.isEmpty()) {
                return createTrackDtoList(purchasedTracks, daoHelper, userId);
            } else {
                return new ArrayList<>();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private List<TrackDto> createTrackDtoList(List<Track> tracks, DaoHelper daoHelper, Long userId) throws DaoException {
        ArtistDao artistDao = daoHelper.createArtistDao();
        OrderDao orderDao = daoHelper.createOrderDao();
        List<TrackDto> trackDtoList = new ArrayList<>();
        for (Track track : tracks) {
            Long trackId = track.getId();
            List<Artist> trackArtists = artistDao.getByTrackId(trackId);

            Optional<Order> optionalOrder = orderDao.getOrderStatusForTrack(userId, trackId);

            TrackStatusEnum trackStatus;
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                if(order.isPaid()){
                    trackStatus = TrackStatusEnum.PURCHASED;
                } else {
                    trackStatus = TrackStatusEnum.ORDERED;
                }
            } else {
                    trackStatus = TrackStatusEnum.AVAILABLE;
            }
            LOGGER.debug("trackStatus " + trackStatus);
            String title = track.getTitle();
            BigDecimal price = track.getPrice();
            TrackDto trackDto = new TrackDto.Builder()
                    .id(trackId)
                    .title(title)
                    .price(price)
                    .artists(trackArtists)
                    .status(trackStatus)
                    .build();
            trackDtoList.add(trackDto);
        }
        return trackDtoList;
    }


}
