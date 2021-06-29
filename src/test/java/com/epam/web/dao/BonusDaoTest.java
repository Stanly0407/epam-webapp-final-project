package com.epam.web.dao;


import com.epam.web.connection.ConnectionFactory;
import com.epam.web.entities.Bonus;
import com.epam.web.entities.BonusType;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.BonusRowMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


public class BonusDaoTest {

    private static final String PROPERTIES_FILENAME = "testDatabase.properties";
    private static final String PROPERTY_URL = "CONNECTION_URL";
    private static final String PROPERTIES_USER = "USER";
    private static final String PROPERTIES_PASSWORD = "PASSWORD";
    private static Connection connection = null;
    private final BonusRowMapper mapper = new BonusRowMapper();
    private final BonusDao bonusDao = new BonusDao(connection, mapper);
    private final Bonus bonusFirst = new Bonus(1L, BonusType.DISCOUNT, 20, false, 1L);
    private final Bonus bonusSecond = new Bonus(2L, BonusType.FREE_TRACK, 5, false, 1L);
    private static final Long TEST_ID = 1L;
    private static final Long USER_TEST_ID = 2L;

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
    public void getUnusedUserBonusesShouldReturnAllUserBonusesList() throws DaoException {
        List<Bonus> expectedList = new ArrayList<>(Arrays.asList(bonusFirst, bonusSecond));
        List<Bonus> actualList = bonusDao.getUnusedUserBonuses(TEST_ID);
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void getUnusedDiscountBonusShouldReturnDiscountBonus() throws DaoException {
        Optional<Bonus> expected = Optional.of(bonusFirst);
        Optional<Bonus> actual = bonusDao.getUnusedDiscountBonus(TEST_ID);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getFreeTrackBonusShouldReturnNothing() throws DaoException {
        Long userIdWithoutBonus = (long) 2;
        Optional<Bonus> actualResult = bonusDao.getUnusedFreeTracksBonus(userIdWithoutBonus);
        Assert.assertFalse(actualResult.isPresent());
    }

    @Test
    public void addDiscountBonusRemoveBonusTest() throws DaoException {
        Bonus expectedNewBonus = new Bonus(null, BonusType.DISCOUNT, 20, false, USER_TEST_ID);
        bonusDao.addDiscountBonus(20, USER_TEST_ID);
        Optional<Bonus> actualNewBonusOptional = bonusDao.getUnusedDiscountBonus(USER_TEST_ID);
        Bonus actualNewBonus = actualNewBonusOptional.get();
        Long newBonusId = actualNewBonus.getId();
        Assert.assertEquals(expectedNewBonus, actualNewBonus);
        bonusDao.removeById(newBonusId);
    }


}