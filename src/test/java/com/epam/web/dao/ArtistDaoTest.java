package com.epam.web.dao;


import com.epam.web.connection.ConnectionFactory;
import com.epam.web.entities.Artist;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.ArtistRowMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class ArtistDaoTest {

    private static final String PROPERTIES_FILENAME = "testDatabase.properties";
    private static final String PROPERTY_URL = "CONNECTION_URL";
    private static final String PROPERTIES_USER = "USER";
    private static final String PROPERTIES_PASSWORD = "PASSWORD";
    private static Connection connection = null;
    private final ArtistRowMapper mapper = new ArtistRowMapper();
    private final ArtistDao artistDao = new ArtistDao(connection, mapper);
    private final Artist artistFirst = new Artist(1L, "Artist_First", "/musicwebapp/artists/filename_one.jpg");
    private final Artist artistSecond = new Artist(2L, "Artist_Second", "/musicwebapp/artists/filename_two.jpg");
    private static final Long TEST_ID = 1L;

    @BeforeClass
    public static void initDatabase() {
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME)) {
            Properties properties = new Properties();
            properties.load(input);
            String url = properties.getProperty(PROPERTY_URL);
            String user = properties.getProperty(PROPERTIES_USER);
            String password = properties.getProperty(PROPERTIES_PASSWORD);
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(url, user, password);
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getAllShouldReturnAllArtistList() throws DaoException {
        List<Artist> expectedList = new ArrayList<>(Arrays.asList(artistFirst, artistSecond));
        List<Artist> actualList = artistDao.getAll();
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void getByTrackIdReturnCorrectResult() throws DaoException {
        List<Artist> expected = new ArrayList<>(Arrays.asList(artistFirst));
        List<Artist> actualList = artistDao.getByTrackId(TEST_ID);
        Assert.assertEquals(expected, actualList);
    }

    @Test
    public void getByNonExistentTrackIdReturnEmptyList() throws DaoException {
        Long nonExistentTrackId = 6L;
        List<Artist> expected = new ArrayList<>();
        List<Artist> actualList = artistDao.getByTrackId(nonExistentTrackId);
        Assert.assertEquals(expected, actualList);
    }

    @Test
    public void getByIdReturnCorrectResult() throws DaoException {
        Optional<Artist> expected = Optional.of(artistFirst);
        Optional<Artist> actualList = artistDao.getById(TEST_ID);
        Assert.assertEquals(expected, actualList);
    }

    @Test
    public void updateArtistNameTest() throws DaoException {
        String newArtistOneName = "testName";
        Artist expectedUpdatedArtist = new Artist(1L, "testName", "/musicwebapp/artists/filename_one.jpg");
        artistDao.updateArtistName(newArtistOneName, TEST_ID);
        Optional<Artist> actualUpdatedArtistOptional = artistDao.getById(TEST_ID);
        Artist actualUpdatedArtist = actualUpdatedArtistOptional.get();
        Assert.assertEquals(expectedUpdatedArtist, actualUpdatedArtist);
        artistDao.updateArtistName("Artist_First", TEST_ID);
    }

    @Test
    public void insertRemoveArtistTest() throws DaoException {
        Artist expectedNewArtist = new Artist(null, "Artist_Third_Test_Insert", "/musicwebapp/artists/filename_three.jpg");
        artistDao.insertArtist("Artist_Third_Test_Insert", "filename_three.jpg");
        Optional<Artist> actualNewArtistOptional = artistDao.findArtistByName("Artist_Third_Test_Insert");
        Artist actualNewArtist = actualNewArtistOptional.get();
        Long newArtistId = actualNewArtist.getId();
        Assert.assertEquals(expectedNewArtist, actualNewArtist);
        artistDao.removeById(newArtistId);
    }


}
