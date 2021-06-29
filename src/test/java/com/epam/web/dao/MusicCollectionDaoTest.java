package com.epam.web.dao;


import com.epam.web.connection.ConnectionFactory;
import com.epam.web.entities.MusicCollection;
import com.epam.web.entities.MusicCollectionType;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.MusicCollectionRowMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;


public class MusicCollectionDaoTest {

    private static final String PROPERTIES_FILENAME = "testDatabase.properties";
    private static final String PROPERTY_URL = "CONNECTION_URL";
    private static final String PROPERTIES_USER = "USER";
    private static final String PROPERTIES_PASSWORD = "PASSWORD";
    private static Connection connection = null;
    private final MusicCollectionRowMapper mapper = new MusicCollectionRowMapper();
    private final MusicCollectionDao musicCollectionDao = new MusicCollectionDao(connection, mapper);
    private final MusicCollection testPlaylist = new MusicCollection(null, MusicCollectionType.PLAYLIST, LocalDate.of(2021, 5, 25), "Playlist1", "/musicwebapp/playlists/testTwo.jpg");
    private static final Long TEST_ID = 2L;

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
    public void getByIdTest() throws DaoException {
        Optional<MusicCollection> expected = Optional.of(testPlaylist);
        Optional<MusicCollection> actual = musicCollectionDao.getPlaylistById(TEST_ID);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void getAllPlaylistsTest() throws DaoException {
        List<MusicCollection> expectedList = new ArrayList<>(Arrays.asList(testPlaylist));
        List<MusicCollection> actualList = musicCollectionDao.getAllPlaylists();
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void insertDeletePlaylistTest() throws DaoException {
        musicCollectionDao.insertPlaylist("2021-05-25", "Playlist1", "testTwo.jpg");
        List<MusicCollection> playlists = musicCollectionDao.getAllPlaylists();
        MusicCollection actual = playlists.get(1);
        Long newPlaylistIdId = actual.getId();
        Assert.assertEquals(testPlaylist, actual);
        musicCollectionDao.removeById(newPlaylistIdId);
    }


}