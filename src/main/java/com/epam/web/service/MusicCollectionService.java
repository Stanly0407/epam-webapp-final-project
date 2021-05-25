package com.epam.web.service;

import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.MusicCollectionDao;
import com.epam.web.entities.MusicCollection;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MusicCollectionService {
    private static final String ALBUM_LIST = "ALBUM";
    private static final String PLAYLIST_LIST = "PLAYLIST";
    private static final String ALBUM_SEARCH_CONDITION = "Album";
    private static final String PLAYLIST_SEARCH_CONDITION = "Playlist";

    private DaoHelperFactory daoHelperFactory;

    public MusicCollectionService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public Optional<MusicCollection> getAlbumById(Long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            return musicCollectionDao.getAlbumById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<MusicCollection> getPlaylistById(Long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            return musicCollectionDao.getPlaylistById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
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

    public void addEditAlbum(String albumId, String releaseDate, String albumTitle, String filename, String artistId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            if (albumId != null && artistId != null) {
                Long id = Long.valueOf(albumId);
                if (filename != null) {
                    musicCollectionDao.updateAlbum(id, releaseDate, albumTitle, filename, artistId);
                } else {
                    musicCollectionDao.updateAlbumInfo(id, releaseDate, albumTitle, artistId);
                }
            } else if (artistId != null) {
                musicCollectionDao.insertAlbum(releaseDate, albumTitle, filename, artistId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteTrackFromCollection(Long trackId, Long collectionId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            musicCollectionDao.deleteTrackFromCollection(trackId, collectionId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void addEditPlaylist(String playlistId, String releaseDate, String playlistTitle, String filename) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            if (playlistId != null) {
                Long id = Long.valueOf(playlistId);
                if (filename != null) {
                    musicCollectionDao.updatePlaylist(id, releaseDate, playlistTitle, filename);
                } else {
                    musicCollectionDao.updatePlaylistInfo(id, releaseDate, playlistTitle);
                }
            } else {
                musicCollectionDao.insertPlaylist(releaseDate, playlistTitle, filename);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void addTrackToCollection(Long trackId, Long albumId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            musicCollectionDao.insertTrackToCollection(trackId, albumId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean checkTrackInAlbum(Long albumId, Long trackId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            Optional<MusicCollection> collection = musicCollectionDao.findAlbumTrack(albumId, trackId);
            return collection.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean checkTrackInPlaylist(Long playlistId, Long trackId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            Optional<MusicCollection> collection = musicCollectionDao.findPlaylistTrack(playlistId, trackId);
            return collection.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<MusicCollection> getAlbumsOfTrackArtists(List<Long> artists) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            MusicCollectionDao musicCollectionDao = daoHelper.createMusicCollectionDao();
            List<MusicCollection> albums = new ArrayList<>();
            for (Long artistId : artists) {
                List<MusicCollection> artistAlbums = musicCollectionDao.getArtistAlbums(artistId);
                albums.addAll(artistAlbums);
            }
            return albums;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
