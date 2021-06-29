package com.epam.web.dao;


import com.epam.web.connection.ConnectionFactory;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.TrackRowMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;


public class TrackDaoTest {

    private static final String PROPERTIES_FILENAME = "testDatabase.properties";
    private static final String PROPERTY_URL = "CONNECTION_URL";
    private static final String PROPERTIES_USER = "USER";
    private static final String PROPERTIES_PASSWORD = "PASSWORD";
    private static Connection connection = null;
    private final TrackRowMapper mapper = new TrackRowMapper();
    private final TrackDao trackDao = new TrackDao(connection, mapper);
    private final Track testTrack = new Track(1L, LocalDate.of(2021, 5, 25), "Track_Artist_First", new BigDecimal("2.00"), "Track_Artist_First.mp3");
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
    public void editTrackInfoTest() throws DaoException {
        Track expected = new Track(1L, LocalDate.of(1995, 5, 25), "TrackEditTest", new BigDecimal("5.00"), "Track_Artist_First.mp3");
        trackDao.editTrackInfo("1995-05-25", "TrackEditTest", "5.00", TEST_ID);
        Optional<Track> actualOptional = trackDao.getTrackByParameters("1995-05-25", "TrackEditTest", "5.00", "Track_Artist_First.mp3");
        Track actual = actualOptional.get();
        Assert.assertEquals(expected, actual);
        trackDao.editTrackInfo("2021-05-25", "Track_Artist_First", "2.00", TEST_ID);
    }

    @Test
    public void findMusicByTrackTest() throws DaoException {
        List<Track> expected = new ArrayList<>(Arrays.asList(testTrack));
        List<Track> actual = trackDao.findMusicByTrack("Track_Artist_First");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findMusicByArtistTest() throws DaoException {
        List<Track> expected = new ArrayList<>(Arrays.asList(testTrack));
        List<Track> actual = trackDao.findMusicByArtist("Artist_First");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAllPaidTracksTestShouldReturnEmptyList() throws DaoException {
        List<Track> expected = new ArrayList<>();
        List<Track> actual = trackDao.findAllPaidTracks(TEST_ID);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void insertRemoveTrackTest() throws DaoException {
        Track expected = new Track(1L, LocalDate.of(2021, 5, 25), "Track_Artist_Third", new BigDecimal("2.00"), "Track_Artist_First.mp3");
        trackDao.insertTrack("2021-05-25", "Track_Artist_Third", "2.00", "Track_Artist_First.mp3");
        List<Track> actualList = trackDao.findMusicByTrack("Track_Artist_Third");
        Track actual = actualList.get(0);
        Assert.assertEquals(expected, actual);
        Long newId = actual.getId();
        trackDao.removeById(newId);
    }

}