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
    private static final int PAGE_LIMIT = 7;
    private static final int LAST_PAGE = 1;
    private static final String TRACK_SEARCH_CONDITION = "Track";
    private static final String ARTIST_SEARCH_CONDITION = "Artist";

    private DaoHelperFactory daoHelperFactory;

    public TrackService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }


    public void addEditTrack(String trackIdParameter, String releaseDate, String title, String price, List<String> artistArray, String filename) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            ArtistDao artistDao = daoHelper.createArtistDao();
            Long trackId;
            Long artistsId;
            List<Long> artists = new ArrayList<>();
            if (!artistArray.isEmpty()) {
                for (String id : artistArray) {
                    artistsId = Long.valueOf(id);
                    artists.add(artistsId);
                }
            }
            daoHelper.startTransaction();
            if (trackIdParameter == null) {
                trackId = null;
                trackDao.insertTrack(releaseDate, title, price, filename);
                Optional<Track> newTrackOptional = trackDao.getTrackByParameters(releaseDate, title, price, filename);
                if (newTrackOptional.isPresent()) {
                    Track track = newTrackOptional.get();
                    trackId = track.getId();
                }
                for (Long artistId : artists) {
                    artistDao.insertArtistsToTrack(trackId, artistId);
                }
            } else {
                trackId = Long.valueOf(trackIdParameter);
                if (filename != null) {
                    trackDao.editTrack(releaseDate, title, price, filename, trackId);
                } else {
                    trackDao.editTrackInfo(releaseDate, title, price, trackId);
                }
                if (!artistArray.isEmpty()) {
                    artistDao.deleteArtistToTrack(trackId);
                    for (Long artistId : artists) {
                        artistDao.insertArtistsToTrack(trackId, artistId);
                    }
                }
            }
            daoHelper.endTransaction();
        } catch (DaoException e) {
            throw new ServiceException("create/update Track error ", e);
        }
    }

    public List<Long> getTrackArtistsIds(Long trackId, Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            Optional<Track> trackOptional = trackDao.getById(trackId);
            List<Long> artistsId = new ArrayList<>();;
            if(trackOptional.isPresent()){
                Track track = trackOptional.get();
                TrackDto trackDto = createTrackDto(track, daoHelper, userId);
                List<Artist> artists = trackDto.getArtists();
                for(Artist artist : artists){
                    Long artistId = artist.getId();
                    artistsId.add(artistId);
                }
            }
            return artistsId;
        } catch (DaoException e) {
            throw new ServiceException(e, e.getMessage());
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
                return createTrackDtoList(searchedTracks, userId);
            } else {
                return new ArrayList<>();
            }
        } catch (DaoException e) {
            throw new ServiceException(e, e.getMessage());
        }
    }

    public List<TrackDto> getArtistTracks(Long artistId, Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> artistTracks = trackDao.findArtistMusic(artistId);
            return createTrackDtoList(artistTracks, userId);
        } catch (DaoException e) {
            throw new ServiceException(e, e.getMessage());
        }
    }

    public List<TrackDto> getCollectionTracks(Long collectionId, Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> albumTracks = trackDao.findCollectionMusic(collectionId);
            return createTrackDtoList(albumTracks, userId);
        } catch (DaoException e) {
            throw new ServiceException(e, e.getMessage());
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
            return createTrackDtoList(tracks, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Track> getOrderedTracksList(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            return trackDao.findOrderedTracks(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<TrackDto> getPurchasedTracks(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> purchasedTracks = trackDao.findAllPaidTracks(userId);
            if (!purchasedTracks.isEmpty()) {
                return createTrackDtoList(purchasedTracks, userId);
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
                return createTrackDtoList(purchasedTracks, userId);
            } else {
                return new ArrayList<>();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean checkPaginationAction(int pageNumber, boolean direction) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            int totalRowsAmount = trackDao.getAllTracks().size();
            int rowCount = ((pageNumber - LAST_PAGE) * PAGE_LIMIT);
            LOGGER.debug("rowCount >= PAGE_LIMIT" + rowCount);
            if (direction) {
                int rowsRemainder = totalRowsAmount - rowCount;
                return rowsRemainder > PAGE_LIMIT;
            } else {
                return rowCount >= PAGE_LIMIT;
            }
        } catch (DaoException e) {
            throw new ServiceException(e, e.getMessage());
        }
    }

    public List<TrackDto> getTracksPage(Long userId, int limit, int pageNumber) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            int offset = ((pageNumber - LAST_PAGE) * PAGE_LIMIT);
            if (offset < 0) {
                offset = 0;
            }
            List<Track> tracks = trackDao.getTracksPage(limit, offset);
            return createTrackDtoList(tracks, userId);
        } catch (DaoException e) {
            throw new ServiceException(e, e.getMessage());
        }
    }

    public List<Integer> getPaginationList() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            int totalRowsAmount = trackDao.getAllTracks().size();
            int pagesAmount;
            if (totalRowsAmount % PAGE_LIMIT > 0) {
                pagesAmount = totalRowsAmount / PAGE_LIMIT + LAST_PAGE;
            } else {
                pagesAmount = totalRowsAmount / PAGE_LIMIT;
            }
            List<Integer> paginationList = new ArrayList<>();
            for (int i = 1; i <= pagesAmount; i++) {
                paginationList.add(i);
            }
            return paginationList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteTrackById(String trackId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            Long id = null;
            if (trackId != null) {
                id = Long.valueOf(trackId);
            }
            trackDao.removeById(id);
        } catch (DaoException e) {
            throw new ServiceException(e, e.getMessage());
        }
    }

    public List<TrackDto> createTrackDtoList(List<Track> tracks, Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            List<TrackDto> trackDtoList = new ArrayList<>();
            for (Track track : tracks) {
                TrackDto trackDto = createTrackDto(track, daoHelper, userId);
                trackDtoList.add(trackDto);
            }
            return trackDtoList;
        } catch (DaoException e) {
            throw new ServiceException(e, e.getMessage());
        }
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
            if (order.getPaid()) {
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
