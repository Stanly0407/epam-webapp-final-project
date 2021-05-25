package com.epam.web.service;

import com.epam.web.dao.*;
import com.epam.web.dto.UserDto;
import com.epam.web.entities.*;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    private static final String PAYMENT_AMOUNT_PATTERN = "^\\d+(.\\d{1,2})?$";
    private static final String CARD_NUMBER_PATTERN = "[0-9]{16}";
    private static final String NAME_PATTERN = "[A-Z]{1,30}";
    private static final String CVV_PATTERN = "[0-9]{3}";

    private DaoHelperFactory daoHelperFactory;

    public UserService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public Optional<User> login(String login, String password) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            return userDao.findByLoginAndPassword(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<UserDto> getAllUsers() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            List<User> users = userDao.getAllUsers();
            return createUserDtoList(users, daoHelper);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<User> getUserInfo(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            return userDao.getById(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void refillUserBalance(String paymentAmountString, Long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            Optional<User> userOptional = userDao.getById(id);
            User user = userOptional.get();
            BigDecimal balance = user.getBalance();
            BigDecimal paymentAmount = new BigDecimal(paymentAmountString);
            BigDecimal updatedUserBalance = balance.add(paymentAmount);
            userDao.updateUserBalance(updatedUserBalance, id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeUserStatus(boolean status, Long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            userDao.updateStatus(status, id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<UserDto> createUserDtoList(List<User> users, DaoHelper daoHelper) throws DaoException {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = createUserDto(user, daoHelper);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    private UserDto createUserDto(User user, DaoHelper daoHelper) throws DaoException {
        Long userId = user.getId();
        String login = user.getLogin();
        String name = user.getName();
        String lastname = user.getLastname();
        BigDecimal balance = user.getBalance();
        boolean status = user.getStatus();
        CommentDao commentDao = daoHelper.createCommentDao();
        List<Comment> allUserComments = commentDao.getUserComments(userId);
        int commentsAmount = allUserComments.size();
        TrackDao trackDao = daoHelper.createTrackDao();
        List<Track> allUserPurchasedTracks = trackDao.findAllPaidTracks(userId);
        int purchasedTracksAmount = allUserPurchasedTracks.size();
        BonusDao bonusDao = daoHelper.createBonusDao();
        List<Bonus> bonuses = bonusDao.getUnusedUserBonuses(userId);
        Bonus bonusDiscount = null;
        Bonus bonusFreeTracks = null;
        if (!bonuses.isEmpty()) {
            for (Bonus bonus : bonuses) {
                BonusType type = bonus.getBonusType();
                if (BonusType.DISCOUNT.equals(type)) {
                    bonusDiscount = bonus;
                } else {
                    bonusFreeTracks = bonus;
                }
            }
        }
        return new UserDto.Builder()
                .id(userId)
                .login(login)
                .name(name)
                .lastname(lastname)
                .balance(balance)
                .commentsAmount(commentsAmount)
                .purchasedTracksAmount(purchasedTracksAmount)
                .bonusDiscount(bonusDiscount)
                .bonusFreeTracks(bonusFreeTracks)
                .status(status)
                .build();
    }

    public String validatePaymentDetails(String paymentAmount, String cardNumber, String nameOnCard, String lastnameOnCard, String cvv) {
        Pattern paymentAmountPattern = Pattern.compile(PAYMENT_AMOUNT_PATTERN);
        Pattern cardNumberPattern = Pattern.compile(CARD_NUMBER_PATTERN);
        Pattern namePattern = Pattern.compile(NAME_PATTERN);
        Pattern cvvPattern = Pattern.compile(CVV_PATTERN);
        String validationResult = "successful";
        if (paymentAmount != null && nameOnCard != null && lastnameOnCard != null && cvv != null) {
            Matcher paymentAmountMatcher = paymentAmountPattern.matcher(paymentAmount);
            Matcher cardNumberMatcher = cardNumberPattern.matcher(cardNumber);
            Matcher nameOnCardMatcher = namePattern.matcher(nameOnCard.toUpperCase());
            Matcher lastnameOnCardMatcher = namePattern.matcher(lastnameOnCard.toUpperCase());
            Matcher cvvOnCardMatcher = cvvPattern.matcher(cvv);
            if (!paymentAmountMatcher.matches()) {
                validationResult = "wrongPaymentAmount";
            } else if (!cardNumberMatcher.matches()) {
                validationResult = "wrongCardNumber";
            } else if (!nameOnCardMatcher.matches()) {
                validationResult = "wrongNameOnCard";
            } else if (!lastnameOnCardMatcher.matches()) {
                validationResult = "wrongLastnameOnCard";
            } else if (!cvvOnCardMatcher.matches()) {
                validationResult = "wrongCvvOnCard";
            } else {
                validationResult = "successful";
            }
        }
        LOGGER.debug("validationResult  " + validationResult);
        return validationResult;
    }

}
