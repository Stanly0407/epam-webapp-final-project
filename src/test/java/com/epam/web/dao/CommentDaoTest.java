package com.epam.web.dao;


import com.epam.web.connection.ConnectionFactory;
import com.epam.web.entities.Comment;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.CommentRowMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;


public class CommentDaoTest {

    private static final String PROPERTIES_FILENAME = "testDatabase.properties";
    private static final String PROPERTY_URL = "CONNECTION_URL";
    private static final String PROPERTIES_USER = "USER";
    private static final String PROPERTIES_PASSWORD = "PASSWORD";
    private static Connection connection = null;
    private final CommentRowMapper mapper = new CommentRowMapper();
    private final CommentDao commentDao = new CommentDao(connection, mapper);
    private final Comment commentFirst = new Comment(1L, LocalDateTime.of(2021, 5, 23, 0, 0, 0), "testOne", 1L, 1L);
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
    public void findCommentsByTrackIdTest() throws DaoException {
        List<Comment> expectedList = new ArrayList<>(Arrays.asList(commentFirst));
        List<Comment> actualList = commentDao.findCommentsByTrackId(TEST_ID);
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void getUserCommentsTest() throws DaoException {
        List<Comment> expectedList = new ArrayList<>(Arrays.asList(commentFirst));
        List<Comment> actualList = commentDao.getUserComments(TEST_ID);
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void updateCommentTest() throws DaoException {
        Comment expected = new Comment(null, LocalDateTime.of(2021, 5, 23, 0, 0, 0), "Updated", 1L, 1L);
        commentDao.updateComment("Updated", TEST_ID);
        Optional<Comment> actualUpdatedCommentOptional = commentDao.getById(TEST_ID);
        Comment actual = actualUpdatedCommentOptional.get();
        Assert.assertEquals(expected, actual);
        commentDao.updateComment("testOne", TEST_ID);
    }


}