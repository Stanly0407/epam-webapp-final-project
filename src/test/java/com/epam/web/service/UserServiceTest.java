package com.epam.web.service;

import com.epam.web.dao.*;
import com.epam.web.dto.UserDto;
import com.epam.web.entities.*;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private static UserDao mockUserDao;
    private static CommentDao mockCommentDao;
    private static TrackDao mockTrackDao;
    private static BonusDao mockBonusDao;
    private static UserService userService;
    private static final Long TEST_ID = 1L;

    private static final String CORRECT_PAYMENT_AMOUNT = "10";
    private static final String CORRECT_CARD_NUMBER = "1111222233334444";
    private static final String CORRECT_NAME_ON_CARD = "SVETLANA";
    private static final String CORRECT_LASTNAME_ON_CARD = "SHELESTOVA";
    private static final String CORRECT_CVV = "123";


    @BeforeClass
    public static void init() {
        DaoHelper mockDaoHelper = Mockito.mock(DaoHelper.class);
        DaoHelperFactory mockDaoHelperFactory = Mockito.mock(DaoHelperFactory.class);
        when(mockDaoHelperFactory.create()).thenReturn(mockDaoHelper);
        mockUserDao = Mockito.mock(UserDao.class);
        mockCommentDao = Mockito.mock(CommentDao.class);
        mockTrackDao = Mockito.mock(TrackDao.class);
        mockBonusDao = Mockito.mock(BonusDao.class);
        when(mockDaoHelper.createUserDao()).thenReturn(mockUserDao);
        when(mockDaoHelper.createCommentDao()).thenReturn(mockCommentDao);
        when(mockDaoHelper.createTrackDao()).thenReturn(mockTrackDao);
        when(mockDaoHelper.createBonusDao()).thenReturn(mockBonusDao);
        userService = new UserService(mockDaoHelperFactory);
    }

    @Test
    public void testGetAllUsers() throws DaoException, ServiceException {
        List<User> userList = new ArrayList<>();
        User firstTestUser = new User(TEST_ID, "firstLogin", "name", "lastname", Role.USER,
                new BigDecimal("10.00"), false);
        User secondTestUser = new User(TEST_ID, "secondLogin", "name", "lastname", Role.USER,
                new BigDecimal("20.00"), false);
        userList.add(firstTestUser);
        userList.add(secondTestUser);
        List<UserDto> expectedUsersDto = new ArrayList<>();
        UserDto expectedFirstUser = new UserDto.Builder()
                .id(TEST_ID).login("firstLogin").name("name").lastname("lastname").balance(new BigDecimal("10.00"))
                .commentsAmount(1).purchasedTracksAmount(1)
                .bonusDiscount(new Bonus(TEST_ID, BonusType.DISCOUNT, 10, false, TEST_ID))
                .bonusFreeTracks(new Bonus(TEST_ID, BonusType.FREE_TRACK, 5, false, TEST_ID)).status(false)
                .build();
        UserDto expectedSecondUser = new UserDto.Builder()
                .id(TEST_ID).login("secondLogin").name("name").lastname("lastname").balance(new BigDecimal("20.00"))
                .commentsAmount(1).purchasedTracksAmount(1)
                .bonusDiscount(new Bonus(TEST_ID, BonusType.DISCOUNT, 10, false, TEST_ID))
                .bonusFreeTracks(new Bonus(TEST_ID, BonusType.FREE_TRACK, 5, false, TEST_ID)).status(false)
                .build();
        expectedUsersDto.add(expectedFirstUser);
        expectedUsersDto.add(expectedSecondUser);
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment());
        List<Track> purchasedTracks = new ArrayList<>();
        purchasedTracks.add(new Track());
        List<Bonus> bonuses = new ArrayList<>();
        bonuses.add(new Bonus(TEST_ID, BonusType.DISCOUNT, 10, false, TEST_ID));
        bonuses.add(new Bonus(TEST_ID, BonusType.FREE_TRACK, 5, false, TEST_ID));

        when(mockUserDao.getAllUsers()).thenReturn(userList);
        when(mockCommentDao.getUserComments(anyLong())).thenReturn(comments);
        when(mockTrackDao.findAllPaidTracks(anyLong())).thenReturn(purchasedTracks);
        when(mockBonusDao.getUnusedUserBonuses(anyLong())).thenReturn(bonuses);

        List<UserDto> actualUsersDto = userService.getAllUsers();

        Assert.assertEquals(expectedUsersDto, actualUsersDto);
    }


    @Test
    public void validatePaymentDetailsShouldReturnSuccessfulResult() {
        String expected = "successful";

        String actual = userService.validatePaymentDetails(CORRECT_PAYMENT_AMOUNT, CORRECT_CARD_NUMBER, CORRECT_NAME_ON_CARD,
                CORRECT_LASTNAME_ON_CARD, CORRECT_CVV);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validatePaymentDetailsShouldReturnWrongPaymentAmount() {
        String WRONG_PAYMENT_AMOUNT = "-10";
        String expected = "wrongPaymentAmount";

        String actual = userService.validatePaymentDetails(WRONG_PAYMENT_AMOUNT, CORRECT_CARD_NUMBER, CORRECT_NAME_ON_CARD,
                CORRECT_LASTNAME_ON_CARD, CORRECT_CVV);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validatePaymentDetailsShouldReturnWrongCardNumber() {
        String WRONG_CARD_NUMBER = "111122223333";
        String expected = "wrongCardNumber";

        String actual = userService.validatePaymentDetails(CORRECT_PAYMENT_AMOUNT, WRONG_CARD_NUMBER, CORRECT_NAME_ON_CARD,
                CORRECT_LASTNAME_ON_CARD, CORRECT_CVV);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validatePaymentDetailsShouldReturnWrongNameOnCard() {
        String WRONG_NAME_ON_CARD = "p1";
        String expected = "wrongNameOnCard";

        String actual = userService.validatePaymentDetails(CORRECT_PAYMENT_AMOUNT, CORRECT_CARD_NUMBER, WRONG_NAME_ON_CARD,
                CORRECT_LASTNAME_ON_CARD, CORRECT_CVV);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validatePaymentDetailsShouldReturnWrongLastnameOnCard() {
        String WRONG_LASTNAME_ON_CARD = "1.2";
        String expected = "wrongLastnameOnCard";

        String actual = userService.validatePaymentDetails(CORRECT_PAYMENT_AMOUNT, CORRECT_CARD_NUMBER, CORRECT_NAME_ON_CARD,
                WRONG_LASTNAME_ON_CARD, CORRECT_CVV);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validatePaymentDetailsShouldReturnWrongCvvOnCard() {
        String WRONG_CVV_ON_CARD = "1A";
        String expected = "wrongCvvOnCard";

        String actual = userService.validatePaymentDetails(CORRECT_PAYMENT_AMOUNT, CORRECT_CARD_NUMBER, CORRECT_NAME_ON_CARD,
                CORRECT_LASTNAME_ON_CARD, WRONG_CVV_ON_CARD);

        Assert.assertEquals(expected, actual);
    }

}
