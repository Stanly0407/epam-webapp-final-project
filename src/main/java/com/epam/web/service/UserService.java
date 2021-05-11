package com.epam.web.service;

import com.epam.web.dao.*;
import com.epam.web.dto.OrderDto;
import com.epam.web.dto.TrackDto;
import com.epam.web.dto.UserDto;
import com.epam.web.entities.*;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

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

    public List<User> getAllUsers() throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();

            return userDao.getAllUsers();
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
            //проверка данных карты, если не соответствует выводить ошибку платежа... pattern
            //проверка соответствует ли paymentAmount формату BigDecimal
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
        if(!bonuses.isEmpty()){
            for(Bonus bonus : bonuses){
                BonusType type = bonus.getBonusType();
                if(BonusType.DISCOUNT.equals(type)){
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



}
