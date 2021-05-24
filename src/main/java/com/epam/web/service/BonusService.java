package com.epam.web.service;

import com.epam.web.dao.BonusDao;
import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.entities.Bonus;
import com.epam.web.entities.BonusType;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BonusService {

    private static final int PERCENT = 100;
    private static final int DEFAULT_DISCOUNT_VALUE = 1;
    private static final int DEFAULT_FREE_TRACK_PRICE = 0;

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

    public Optional<Bonus> getUnusedUserDiscountBonus(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BonusDao bonusDao = daoHelper.createBonusDao();
            return bonusDao.getUnusedDiscountBonus(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<Bonus> getUnusedFreeTracksBonus(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BonusDao bonusDao = daoHelper.createBonusDao();
            return bonusDao.getUnusedFreeTracksBonus(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteUserBonus(Long bonusId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BonusDao bonusDao = daoHelper.createBonusDao();
            bonusDao.removeById(bonusId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void addBonus(Long userId, int amount, BonusType type) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BonusDao bonusDao = daoHelper.createBonusDao();
            if (BonusType.DISCOUNT.equals(type)) {
                bonusDao.addDiscountBonus(amount, userId);
            } else if (BonusType.FREE_TRACK.equals(type)) {
                bonusDao.addFreeTrackBonus(amount, userId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e, e.getMessage());
        }
    }

    public List<Track> applyBonusDiscount(List<Track> tracks, Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BonusDao bonusDao = daoHelper.createBonusDao();
            Optional<Bonus> bonusOptional = bonusDao.getUnusedDiscountBonus(userId);
            BigDecimal discount = new BigDecimal(DEFAULT_DISCOUNT_VALUE);
            if (bonusOptional.isPresent()) {
                Bonus bonus = bonusOptional.get();
                int bonusAmount = PERCENT - bonus.getAmount();
                double discountDouble = ((double) bonusAmount / PERCENT);
                discount = new BigDecimal(discountDouble);

            }
            List<Track> updatedTrackList = new ArrayList<>();
            for (Track track : tracks) {
                BigDecimal oldPrice = track.getPrice();
                BigDecimal newPrice = discount.multiply(oldPrice).setScale(2, RoundingMode.HALF_UP);
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
            }
            BigDecimal defaultFreeTrackPrice = new BigDecimal(DEFAULT_FREE_TRACK_PRICE);
            for (int i = freeTracksAmount - 1; i >= 0; i--) {
                Track track = tracks.get(i);
                track.setPrice(defaultFreeTrackPrice);
            }
            return tracks;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
