package com.epam.web.service;

import com.epam.web.dao.*;
import com.epam.web.entities.*;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class OrderServiceTest {
    private static OrderDao mockOrderDao;
    private static UserDao mockUserDao;
    private static BonusDao mockBonusDao;
    private static TrackDao mockTrackDao;
    private static OrderService orderService;
    private static final Long TEST_ID = 1L;


    @BeforeClass
    public static void init() throws DaoException {
        DaoHelper mockDaoHelper = Mockito.mock(DaoHelper.class);
        DaoHelperFactory mockDaoHelperFactory = Mockito.mock(DaoHelperFactory.class);
        when(mockDaoHelperFactory.create()).thenReturn(mockDaoHelper);
        mockOrderDao = Mockito.mock(OrderDao.class);
        mockUserDao = Mockito.mock(UserDao.class);
        mockBonusDao = Mockito.mock(BonusDao.class);
        mockTrackDao = Mockito.mock(TrackDao.class);
        when(mockDaoHelper.createOrderDao()).thenReturn(mockOrderDao);
        when(mockDaoHelper.createUserDao()).thenReturn(mockUserDao);
        when(mockDaoHelper.createBonusDao()).thenReturn(mockBonusDao);
        when(mockDaoHelper.createTrackDao()).thenReturn(mockTrackDao);
        doNothing().when(mockDaoHelper).startTransaction();
        doNothing().when(mockDaoHelper).endTransaction();
        orderService = new OrderService(mockDaoHelperFactory);

        doNothing().when(mockBonusDao).changeUserBonusStatus(anyLong());
    }

    @Test
    public void testPayOrderShouldReturnTrue() throws DaoException, ServiceException {
        List<Track> tracks = new ArrayList<>();
        Track testTrackFirst = new Track(TEST_ID, LocalDate.of(2021, 5, 25), "Track_Artist_First",
                new BigDecimal("10.00"), "Track_Artist_First.mp3");
        Track testTrackSecond = new Track(TEST_ID, LocalDate.of(2021, 5, 25), "Track_Artist_First",
                new BigDecimal("20.00"), "Track_Artist_First.mp3");
        tracks.add(testTrackFirst);
        tracks.add(testTrackSecond);
        when(mockTrackDao.findOrderedTracks(anyLong())).thenReturn(tracks);
        Bonus testDiscountBonus = new Bonus(TEST_ID, BonusType.DISCOUNT, 10, false, TEST_ID);
        Bonus testFreeTracksBonus = new Bonus(TEST_ID, BonusType.FREE_TRACK, 1, false, TEST_ID);
        when(mockBonusDao.getUnusedDiscountBonus(anyLong())).thenReturn(Optional.of(testDiscountBonus));
        when(mockBonusDao.getUnusedFreeTracksBonus(anyLong())).thenReturn(Optional.of(testFreeTracksBonus));

        User testUser = new User(TEST_ID, "firstLogin", "name", "lastname", Role.USER,
                new BigDecimal("100.00"), false);
        when(mockUserDao.getById(anyLong())).thenReturn(Optional.of(testUser));

        boolean expected = orderService.payOrder(TEST_ID, TEST_ID, true, true);

        Assert.assertTrue(expected);
    }

    @Test
    public void testPayOrderShouldReturnFalseNotEnoughBalance() throws DaoException, ServiceException {
        List<Track> tracks = new ArrayList<>();
        Track testTrackFirst = new Track(TEST_ID, LocalDate.of(2021, 5, 25), "Track_Artist_First",
                new BigDecimal("10.00"), "Track_Artist_First.mp3");
        Track testTrackSecond = new Track(TEST_ID, LocalDate.of(2021, 5, 25), "Track_Artist_First",
                new BigDecimal("20.00"), "Track_Artist_First.mp3");
        tracks.add(testTrackFirst);
        tracks.add(testTrackSecond);
        when(mockTrackDao.findOrderedTracks(anyLong())).thenReturn(tracks);
        Bonus testDiscountBonus = new Bonus(TEST_ID, BonusType.DISCOUNT, 10, false, TEST_ID);
        Bonus testFreeTracksBonus = new Bonus(TEST_ID, BonusType.FREE_TRACK, 1, false, TEST_ID);
        when(mockBonusDao.getUnusedDiscountBonus(anyLong())).thenReturn(Optional.of(testDiscountBonus));
        when(mockBonusDao.getUnusedFreeTracksBonus(anyLong())).thenReturn(Optional.of(testFreeTracksBonus));

        User userWithNotEnoughBalance = new User(TEST_ID, "firstLogin", "name", "lastname", Role.USER,
                new BigDecimal("10.00"), false);
        when(mockUserDao.getById(anyLong())).thenReturn(Optional.of(userWithNotEnoughBalance));

        boolean expected = orderService.payOrder(TEST_ID, TEST_ID, true, true);

        Assert.assertFalse(expected);
    }

}
