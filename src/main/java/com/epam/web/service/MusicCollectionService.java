package com.epam.web.service;

import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.MusicCollectionDao;
import com.epam.web.dao.TrackDao;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.MusicCollection;
import com.epam.web.entities.MusicCollectionType;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class MusicCollectionService {
    private static final String ALBUM_LIST = "ALBUM";
    private static final String PLAYLIST_LIST = "PLAYLIST";
    private static final String ALBUM_SEARCH_CONDITION = "Album";
    private static final String PLAYLIST_SEARCH_CONDITION = "Playlist";
    private static final int PAGE_LIMIT = 7;
    private static final int LAST_PAGE = 1;

    private DaoHelperFactory daoHelperFactory;

    public MusicCollectionService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public List<MusicCollection> getAllAlbums() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            return musicCollectionDao.getAllAlbums();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<MusicCollection> getAllPlaylists() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            return musicCollectionDao.getAllPlaylists();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<MusicCollection> getNewMusicCollections(String collectionType) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            List<MusicCollection> musicCollections = null;
            switch (collectionType) {
                case ALBUM_LIST:
                    musicCollections = musicCollectionDao.findFiveNewAlbums();
                    break;
                case PLAYLIST_LIST:
                    musicCollections = musicCollectionDao.findFiveNewPlaylists();
                    break;
            }
            return musicCollections;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<MusicCollection> getMusicByCondition(String searchSubject, String searchCondition) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            List<MusicCollection> searchedTracks = null;
            switch (searchCondition) {
                case ALBUM_SEARCH_CONDITION:
                    searchedTracks = musicCollectionDao.findMusicByAlbumTitle(searchSubject);
                    break;
                case PLAYLIST_SEARCH_CONDITION:
                    searchedTracks = musicCollectionDao.findMusicByPlaylistTitle(searchSubject);
                    break;
            }
            return searchedTracks;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void addAlbum(String releaseDate, String albumTitle, String filename, String artistId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            musicCollectionDao.insertAlbum(releaseDate, albumTitle, filename, artistId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void addPlaylist(String releaseDate, String albumTitle, String filename) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            musicCollectionDao.insertPlaylist(releaseDate, albumTitle, filename);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean checkPaginationAction(int pageNumber, boolean direction, MusicCollectionType type) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            int totalRowsAmount;
            if(type.equals(MusicCollectionType.ALBUM)){
                totalRowsAmount = musicCollectionDao.getAllAlbums().size();
            } else {
                totalRowsAmount = musicCollectionDao.getAllPlaylists().size();
            }
            int rowCount = ((pageNumber - LAST_PAGE) * PAGE_LIMIT);
            if (direction) {
                int rowsRemainder = totalRowsAmount - rowCount;
                return rowsRemainder > PAGE_LIMIT;
            } else {
                return rowCount >= PAGE_LIMIT;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Integer> getPaginationList(MusicCollectionType collectionType) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            int totalRowsAmount;
            if(MusicCollectionType.ALBUM.equals(collectionType)){
                totalRowsAmount = musicCollectionDao.getAllAlbums().size();
            } else {
                totalRowsAmount = musicCollectionDao.getAllPlaylists().size();
            }
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

    public List<MusicCollection> getAlbumsPage(int limit, int pageNumber, MusicCollectionType musicCollectionType) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            int offset = ((pageNumber - LAST_PAGE) * PAGE_LIMIT);
            if (offset < 0) {
                offset = 0;
            }
            List<MusicCollection> musicCollectionList;
            if(MusicCollectionType.ALBUM.equals(musicCollectionType)){
                return musicCollectionList = musicCollectionDao.getAlbumsPage(limit, offset);
            } else {
                return musicCollectionList = musicCollectionDao.getPlaylistsPage(limit, offset);
            }

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


}
