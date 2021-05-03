package com.epam.web.service;

import com.epam.web.dao.*;
import com.epam.web.dto.TrackDto;
import com.epam.web.dto.TrackStatus;
import com.epam.web.entities.Artist;
import com.epam.web.entities.Comment;
import com.epam.web.entities.Order;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    //  public void addTrack(String releaseDate, String title, String price, String[] artistsIds, String filename) throws ServiceException {
    public void addTrack(String releaseDate, String title, String price, String artistsIds, String filename) throws ServiceException {

        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            ArtistDao artistDao = daoHelper.createArtistDao();
            daoHelper.startTransaction();
            trackDao.insertTrack(releaseDate, title, price, filename);
            Optional<Track> newTrackOptional = trackDao.getTrackByParameters(releaseDate, title, price, filename);
            Long newTrackId = null;
            if (newTrackOptional.isPresent()) {
                Track track = newTrackOptional.get();
                newTrackId = track.getId();
            }

//            LOGGER.debug("artistsIds " + Arrays.toString(artistsIds));
//            int trackArtistsNumber = artistsIds.length;
//            for (int i = 0; i <= trackArtistsNumber; i++) {
//                String artistIdString = artistsIds[i];
//                Long artistId = Long.valueOf(artistIdString);
//                artistDao.insertArtistsToTrack(newTrackId, artistId);
//            }
            Long artistsId = Long.valueOf(artistsIds);
            artistDao.insertArtistsToTrack(newTrackId, artistsId);
            daoHelper.endTransaction();
        } catch (DaoException e) {
            LOGGER.debug("createTrack " + e);
            throw new ServiceException(e);
        }
    }

    public List<TrackDto> getMusicByCondition(String searchSubject, String searchCondition, Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
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

    public List<TrackDto> getArtistTracks(Long artistId, Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> artistTracks = trackDao.findArtistMusic(artistId);
            return createTrackDtoList(artistTracks, daoHelper, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<TrackDto> getCollectionTracks(Long collectionId, Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> albumTracks = trackDao.findCollectionMusic(collectionId);
            return createTrackDtoList(albumTracks, daoHelper, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<TrackDto> getAllTracks(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> tracks = trackDao.getAllTracks();
            return createTrackDtoList(tracks, daoHelper, userId);
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

    public TrackDto getTrackDtoById(Long trackId, Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            Optional<Track> optionalTrack = trackDao.getById(trackId);
            Track track = new Track();
            if (optionalTrack.isPresent()) {
                track = optionalTrack.get();
            }
            return createTrackDto(track, daoHelper, userId);
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
            List<Track> purchasedTracks = trackDao.findAllPaidTracks(userId);
            if (!purchasedTracks.isEmpty()) {
                return createTrackDtoList(purchasedTracks, daoHelper, userId);
            } else {
                return new ArrayList<>();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<TrackDto> getTracksFromPaidOrder(Long orderId, Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> purchasedTracks = trackDao.findPaidTracksByOrderId(orderId);
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
        List<TrackDto> trackDtoList = new ArrayList<>();
        for (Track track : tracks) {
            TrackDto trackDto = createTrackDto(track, daoHelper, userId);
            trackDtoList.add(trackDto);
        }
        return trackDtoList;
    }

    private TrackDto createTrackDto(Track track, DaoHelper daoHelper, Long userId) throws DaoException {
        ArtistDao artistDao = daoHelper.createArtistDao();
        OrderDao orderDao = daoHelper.createOrderDao();
        CommentDao commentDao = daoHelper.createCommentDao();
        Long trackId = track.getId();
        List<Artist> trackArtists = artistDao.getByTrackId(trackId);
        Optional<Order> optionalOrder = orderDao.getOrderStatusForTrack(userId, trackId);
        TrackStatus trackStatus;
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (order.isPaid()) {
                trackStatus = TrackStatus.PURCHASED;
            } else {
                trackStatus = TrackStatus.ORDERED;
            }
        } else {
            trackStatus = TrackStatus.AVAILABLE;
        }
        LocalDate releaseDate = track.getReleaseDate();
        String title = track.getTitle();
        List<Comment> comments = commentDao.findCommentsByTrackId(trackId);
        int commentsAmount = comments.size();
        BigDecimal price = track.getPrice();
        String filename = track.getFilename();
        return new TrackDto.Builder()
                .id(trackId)
                .releaseDate(releaseDate)
                .title(title)
                .commentsAmount(commentsAmount)
                .price(price)
                .filename(filename)
                .artists(trackArtists)
                .status(trackStatus)
                .build();
    }
}
