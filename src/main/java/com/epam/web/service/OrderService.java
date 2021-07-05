package com.epam.web.service;

import com.epam.web.dao.*;
import com.epam.web.entities.Bonus;
import com.epam.web.entities.Order;
import com.epam.web.entities.Track;
import com.epam.web.entities.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);
    private static final int ONE_HUNDRED_PERCENT = 100;

    private DaoHelperFactory daoHelperFactory;

    public OrderService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public void deleteTrackFromCart(Long orderId, Long trackId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            orderDao.deleteTrackFromOrder(orderId, trackId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void addTrackToCart(Long userId, Long trackId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            TrackDao trackDao = daoHelper.createTrackDao();
            Optional<Order> optionalOrder = orderDao.getCurrentOrder(userId);
            Long orderId;
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                orderId = order.getId();
            } else {
                orderDao.createOrder(userId);
                Optional<Order> optionalNewOrder = orderDao.getCurrentOrder(userId);
                Order order = optionalNewOrder.get();
                orderId = order.getId();
            }
            Optional<Track> optionalTrack = trackDao.getTrackFromCart(userId, trackId);
            if (!optionalTrack.isPresent()) {
                orderDao.addTrackToOrder(orderId, trackId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e, e.getMessage());
        }
    }

    public boolean payOrder(Long orderId, Long userId, boolean activatedDiscountBonus, boolean activatedFreeTracksBonus) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            UserDao userDao = daoHelper.createUserDao();
            BonusDao bonusDao = daoHelper.createBonusDao();
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> tracks = trackDao.findOrderedTracks(userId);
            boolean payResult = false;
            Bonus discount = null;
            Bonus freeTracks = null;
            if (activatedDiscountBonus) {
                Optional<Bonus> discountOptional = bonusDao.getUnusedDiscountBonus(userId);
                discount = discountOptional.get();
            }
            if (activatedFreeTracksBonus) {
                Optional<Bonus> freeTracksOptional = bonusDao.getUnusedFreeTracksBonus(userId);
                freeTracks = freeTracksOptional.get();
            }
            LOGGER.debug("freeTracks " + freeTracks);
            BigDecimal orderAmount = countOrderTotalSum(tracks, discount, freeTracks);
            Optional<User> userOptional = userDao.getById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                BigDecimal userBalance = user.getBalance();
                BigDecimal balanceAfterPay = userBalance.subtract(orderAmount); //new balance
                BigDecimal emptyBalance = new BigDecimal(0);
                if (balanceAfterPay.compareTo(emptyBalance) >= 0) {
                    daoHelper.startTransaction();
                    userDao.updateUserBalance(balanceAfterPay, userId);
                    orderDao.updateOrderStatus(orderId);
                    if (discount != null) {
                        bonusDao.changeUserBonusStatus(discount.getId());
                    }
                    if (freeTracks != null) {
                        bonusDao.changeUserBonusStatus(freeTracks.getId());
                    }
                    daoHelper.endTransaction();
                    payResult = true;
                } else {
                    payResult = false;
                }
            }
            return payResult;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Long getCurrentCartId(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            Optional<Order> optionalOrder = orderDao.getCurrentOrder(userId);
            Long orderId = null;
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                orderId = order.getId();
            }
            return orderId;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public BigDecimal countOrderTotalSum(List<Track> tracks, Bonus discount, Bonus freeTracks) {
        if (freeTracks != null) {
            int freeTracksAmount = freeTracks.getAmount();
            tracks.sort(Comparator.comparing(Track::getPrice));
            tracks.subList(0, freeTracksAmount).clear();
        }
        LOGGER.debug("tracks " + tracks);

        BigDecimal total = new BigDecimal("0.00");
        if (!tracks.isEmpty()){
            total = sumOfOrderedTracks(tracks);}

        LOGGER.debug("total " + total);
        if (discount != null) {
            int bonusAmount = ONE_HUNDRED_PERCENT - discount.getAmount();
            double discountDouble = ((double) bonusAmount / ONE_HUNDRED_PERCENT);
            BigDecimal discountAmount = new BigDecimal(discountDouble).setScale(2, RoundingMode.HALF_UP);
            total = discountAmount.multiply(total).setScale(2, RoundingMode.HALF_UP);
        }

        return total;
    }

    public BigDecimal sumOfOrderedTracks(List<Track> orderedTracks) {
        return orderedTracks
                .stream()
                .map(Track::getPrice)
                .reduce(BigDecimal::add)
                .get();
    }

}
