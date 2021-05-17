package com.epam.web.dao;

import com.epam.web.entities.Bonus;
import com.epam.web.entities.Order;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class BonusDao extends AbstractDao<Bonus> implements Dao<Bonus> {

    private static final String UPDATE_BONUS_STATUS = "UPDATE bonus SET status_use = true where id=?";
    private static final String INSERT_DISCOUNT_BONUS = "INSERT INTO bonus (bonus_type, amount, status_use, user_id) " +
            "VALUE ('DISCOUNT', ?, default, ?)";
    private static final String INSERT_FREE_TRACK_BONUS = "INSERT INTO bonus (bonus_type, amount, status_use, user_id) " +
            "VALUE ('FREE_TRACK', ?, default, ?)";
    private static final String FIND_UNUSED_USER_BONUSES = "SELECT id, bonus_type, amount, status_use, user_id FROM bonus " +
            "WHERE status_use = false AND user_id = ?";
    private static final String FIND_UNUSED_DISCOUNT_BONUS = "SELECT id, bonus_type, amount, status_use, user_id FROM bonus " +
            "WHERE status_use = false AND user_id = ? AND bonus_type = 'DISCOUNT'";
    private static final String FIND_UNUSED_FREE_TRACKS_BONUS = "SELECT id, bonus_type, amount, status_use, user_id FROM bonus " +
            "WHERE status_use = false AND user_id = ? AND bonus_type = 'FREE_TRACK'";
    private static final String DELETE_BONUS = "DELETE FROM bonus WHERE id=?";

    public BonusDao(Connection connection, RowMapper<Bonus> mapper) {
        super(connection, mapper);
    }


    public List<Bonus> getUnusedUserBonuses(Long userId) throws DaoException {
        return executeQuery(FIND_UNUSED_USER_BONUSES, userId);
    }

    public Optional<Bonus> getUnusedDiscountBonus(Long userId) throws DaoException {
        return executeForSingleResult(FIND_UNUSED_DISCOUNT_BONUS, userId);
    }

    public Optional<Bonus> getUnusedFreeTracksBonus(Long userId) throws DaoException {
        return executeForSingleResult(FIND_UNUSED_FREE_TRACKS_BONUS, userId);
    }

    public void addDiscountBonus(int amount, Long userId) throws DaoException {
        executeUpdate(INSERT_DISCOUNT_BONUS, amount, userId);
    }

    public void addFreeTrackBonus(int amount, Long userId) throws DaoException {
        executeUpdate(INSERT_FREE_TRACK_BONUS, amount, userId);
    }

    public void changeUserBonusStatus(Long bonusId) throws DaoException {
        executeUpdate(UPDATE_BONUS_STATUS, bonusId);
    }

    public void deleteBonus(Long bonusId) throws DaoException {
        executeUpdate(DELETE_BONUS, bonusId);
    }


    @Override
    public Optional<Bonus> getById(Long id) throws DaoException {
        return executeForSingleResult(UPDATE_BONUS_STATUS, id); //change
    }

    @Override
    public void save(Bonus entity) {
    }

    @Override
    public void removeById(Long id) {
    }

    @Override
    protected String getTableName() {
        return Bonus.TABLE;
    }
}
