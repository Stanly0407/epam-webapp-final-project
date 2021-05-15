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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);
    private static final int PERCENT = 100;
    private static final int DEFAULT_DISCOUNT_VALUE = 1;
    private static final int DEFAULT_FREE_TRACK_PRICE = 0;

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
            // проверка добавлен ли трек уже в корзину, чтобы не было повтора. (не удалять)
            Optional<Track> optionalTrack = trackDao.getTrackFromCart(userId, trackId);
            if (!optionalTrack.isPresent()) {
                orderDao.addTrackToOrder(orderId, trackId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e, e.getMessage());
        }
    }

// перенести в бонус сервис
    public List<Track> applyBonusDiscount(List<Track> tracks, Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BonusDao bonusDao = daoHelper.createBonusDao();
            Optional<Bonus> bonusOptional = bonusDao.getUnusedDiscountBonus(userId);
            BigDecimal discount = new BigDecimal(DEFAULT_DISCOUNT_VALUE);
            if (bonusOptional.isPresent()) {
                Bonus bonus = bonusOptional.get();
                discount = new BigDecimal(bonus.getAmount() / PERCENT);
            }

            List<Track> updatedTrackList = new ArrayList<>();
            for(Track track : tracks){
                BigDecimal oldPrice = track.getPrice();
                BigDecimal newPrice = oldPrice.multiply(discount);
                track.setPrice(newPrice);
                updatedTrackList.add(track);
            }
            return updatedTrackList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    public List<Track> applyBonusFreeTracks(List<Track> tracks, Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BonusDao bonusDao = daoHelper.createBonusDao();
            Optional<Bonus> bonusOptional = bonusDao.getUnusedFreeTracksBonus(userId);
            int freeTracksAmount = 0;
            if (bonusOptional.isPresent()) {
                Bonus bonus = bonusOptional.get();
                freeTracksAmount = bonus.getAmount();
            };
            BigDecimal defaultFreeTrackPrice = new BigDecimal(DEFAULT_FREE_TRACK_PRICE);
            for(int i = freeTracksAmount; i >= 0; i--){
                Track track = tracks.get(i);
                track.setPrice(defaultFreeTrackPrice);
            }
            return tracks;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    public boolean payOrder(Long orderId, Long userId, Long bonusDiscountId, Long bonusFreeTracksId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            UserDao userDao = daoHelper.createUserDao();

            BonusDao bonusDao = daoHelper.createBonusDao();

            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> tracks = trackDao.findOrderedTracks(userId);

            boolean payResult = false;
            BigDecimal orderAmount = countOrderTotalPrice(userId, bonusDiscountId, bonusFreeTracksId);
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
                    daoHelper.endTransaction();
                    payResult = true;
                } else {
                    payResult = false;
                }
            }
            return payResult;
        } catch (
                DaoException e) {
            throw new ServiceException(e);
        }
    }


    public boolean checkFreeTracksBonus(Long userId, Long bonusFreeTracksId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            BonusDao bonusDao = daoHelper.createBonusDao();
            List<Track> tracks = trackDao.findOrderedTracks(userId);
            int freeTracksAmount = 0;
            Optional<Bonus> bonusOptional = bonusDao.getById(bonusFreeTracksId);
            if (bonusOptional.isPresent()) {
                Bonus bonus = bonusOptional.get();
                freeTracksAmount = bonus.getAmount();
            }
            return freeTracksAmount >= tracks.size();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    public List<OrderDto> getPaidOrders(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Order> paidOrdersList = orderDao.findPaidOrders(userId);
            List<OrderDto> paidOrders = new ArrayList<>();
            for (Order order : paidOrdersList) {
                Long orderId = order.getId();
                List<Track> tracks = trackDao.findPaidTracksByOrderId(orderId);
                BigDecimal totalSum = tracks.stream().map(Track::getPrice).reduce(BigDecimal::add).get();
                int paidTracksAmount = tracks.size();
                OrderDto orderDto = createOrderDto(order, totalSum, paidTracksAmount);
                paidOrders.add(orderDto);
            }
            return paidOrders;
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

    public BigDecimal sumOfOrderedTracks(List<Track> orderedTracks) {
        return orderedTracks
                .stream()
                .map(Track::getPrice)
                .reduce(BigDecimal::add)
                .get();
    }

    private OrderDto createOrderDto(Order order, BigDecimal totalSum, int paidTracksAmount) throws DaoException {
        Long id = order.getId();
        LocalDateTime orderDate = order.getOrderDate();
        return new OrderDto.Builder()
                .id(id)
                .orderDate(orderDate)
                .tracksAmount(paidTracksAmount)
                .totalSum(totalSum)
                .build();
    }

}
