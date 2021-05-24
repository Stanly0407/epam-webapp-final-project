package com.epam.web.service;

import com.epam.web.dao.ArtistDao;
import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.entities.Artist;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ArtistServiceTest {
    private static DaoHelperFactory mockDaoHelperFactory;
    private static DaoHelper mockDaoHelper;
    private static ArtistDao mockArtistDao;
    private static ArtistService artistService;
    private static Artist mockArtist;
    private final static Long MOCK_ID = (long) 1;

    @BeforeClass
    public static void init() {
        mockArtistDao = Mockito.mock(ArtistDao.class);
        mockDaoHelper = Mockito.mock(DaoHelper.class);
        mockDaoHelperFactory = Mockito.mock(DaoHelperFactory.class);
        when(mockDaoHelperFactory.create()).thenReturn(mockDaoHelper);
        when(mockDaoHelper.createArtistDao()).thenReturn(mockArtistDao);
        artistService = new ArtistService(mockDaoHelperFactory);
        mockArtist = Mockito.mock(Artist.class);
    }


    @Test
    public void testGetArtistById() throws DaoException, ServiceException {
        when(mockArtistDao.getById(anyLong())).thenReturn(Optional.of(mockArtist));
        Artist artist = artistService.getArtistById(MOCK_ID);
        Assert.assertNotNull(artist);
    }


}
