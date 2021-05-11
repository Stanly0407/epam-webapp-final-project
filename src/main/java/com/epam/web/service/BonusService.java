package com.epam.web.service;

import com.epam.web.dao.*;
import com.epam.web.dto.OrderDto;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Bonus;
import com.epam.web.entities.Order;
import com.epam.web.entities.Track;
import com.epam.web.entities.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BonusService {
    private static final Logger LOGGER = LogManager.getLogger(BonusService.class);

    private DaoHelperFactory daoHelperFactory;

    public BonusService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public List<Bonus> getUnusedUserBonuses(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BonusDao bonusDao = daoHelper.createBonusDao();
            return bonusDao.getUnusedUserBonuses(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteUserBonus(Long orderId, Long trackId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            orderDao.deleteTrackFromOrder(orderId, trackId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void addBonusToUser(Long userId, Long trackId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
                orderDao.addTrackToOrder(orderId, trackId);
        } catch (DaoException e) {
            LOGGER.debug(e + " ________ " + e.getMessage());
            throw new ServiceException(e, e.getMessage());
        }
    }


    public void changeBonusStatusUse(Long userId, Long trackId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            orderDao.addTrackToOrder(orderId, trackId);
        } catch (DaoException e) {
            LOGGER.debug(e + " ________ " + e.getMessage());
            throw new ServiceException(e, e.getMessage());
        }
    }

}
