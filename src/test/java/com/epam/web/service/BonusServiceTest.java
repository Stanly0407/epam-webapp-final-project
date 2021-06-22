package com.epam.web.service;

import com.epam.web.dao.BonusDao;
import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.entities.Bonus;
import com.epam.web.entities.BonusType;
import com.epam.web.entities.Track;
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
import static org.mockito.Mockito.when;

public class BonusServiceTest {
    private static BonusDao mockBonusDao;
    private static BonusService bonusService;
    private static Bonus testDiscountBonus;
    private static Bonus testFreeTracksBonus;
    private final static Long TEST_ID = 1L;

    @BeforeClass
    public static void init() {
        mockBonusDao = Mockito.mock(BonusDao.class);
        DaoHelper mockDaoHelper = Mockito.mock(DaoHelper.class);
        DaoHelperFactory mockDaoHelperFactory = Mockito.mock(DaoHelperFactory.class);
        when(mockDaoHelperFactory.create()).thenReturn(mockDaoHelper);
        when(mockDaoHelper.createBonusDao()).thenReturn(mockBonusDao);
        bonusService = new BonusService(mockDaoHelperFactory);
        testDiscountBonus = new Bonus(TEST_ID, BonusType.DISCOUNT, 10, false, TEST_ID);
        testFreeTracksBonus = new Bonus(TEST_ID, BonusType.FREE_TRACK, 1, false, TEST_ID);
    }

    @Test
    public void testApplyBonusDiscount() throws DaoException, ServiceException {
        List<Track> discountTestedTracks = new ArrayList<>();
        discountTestedTracks.add(new Track(TEST_ID, LocalDate.of(2021, 5, 25), "Track_Artist_First",
                new BigDecimal("10.00"), "Track_Artist_First.mp3"));
        discountTestedTracks.add(new Track(TEST_ID, LocalDate.of(2021, 5, 25), "Track_Artist_Second",
                new BigDecimal("100.00"), "Track_Artist_Second.mp3"));
        List<Track> expectedTracks = new ArrayList<>();
        expectedTracks.add(new Track(TEST_ID, LocalDate.of(2021, 5, 25), "Track_Artist_First",
                new BigDecimal("9.00"), "Track_Artist_First.mp3"));
        expectedTracks.add(new Track(TEST_ID, LocalDate.of(2021, 5, 25), "Track_Artist_Second",
                new BigDecimal("90.00"), "Track_Artist_Second.mp3"));

        when(mockBonusDao.getUnusedDiscountBonus(anyLong())).thenReturn(Optional.of(testDiscountBonus));
        List<Track> actualTracks = bonusService.applyBonusDiscount(discountTestedTracks, TEST_ID);

        Assert.assertEquals(expectedTracks, actualTracks);
    }

    @Test
    public void applyBonusFreeTracks() throws DaoException, ServiceException {
        List<Track> freeTracksTestedTracks = new ArrayList<>();
        freeTracksTestedTracks.add(new Track(TEST_ID, LocalDate.of(2021, 5, 25), "Track_Artist_First",
                new BigDecimal("10.00"), "Track_Artist_First.mp3"));
        freeTracksTestedTracks.add(new Track(TEST_ID, LocalDate.of(2021, 5, 25), "Track_Artist_Second",
                new BigDecimal("100.00"), "Track_Artist_Second.mp3"));
        List<Track> expectedTracks = new ArrayList<>();
        expectedTracks.add(new Track(TEST_ID, LocalDate.of(2021, 5, 25), "Track_Artist_First",
                new BigDecimal("0"), "Track_Artist_First.mp3"));
        expectedTracks.add(new Track(TEST_ID, LocalDate.of(2021, 5, 25), "Track_Artist_Second",
                new BigDecimal("100.00"), "Track_Artist_Second.mp3"));

        when(mockBonusDao.getUnusedFreeTracksBonus(anyLong())).thenReturn(Optional.of(testFreeTracksBonus));
        List<Track> actualTracks = bonusService.applyBonusFreeTracks(freeTracksTestedTracks, TEST_ID);

        Assert.assertEquals(expectedTracks, actualTracks);
    }


}
