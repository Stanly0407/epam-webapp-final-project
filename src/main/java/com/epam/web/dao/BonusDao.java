package com.epam.web.dao;

import com.epam.web.entities.Bonus;
import com.epam.web.entities.Order;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class BonusDao extends AbstractDao<Bonus> implements Dao<Bonus> {

    private static final String UPDATE_CURRENT_ORDER = "UPDATE track SET title=?, description=?, price=?, filename=? where id=?";
    private static final String INSERT_ORDER = "INSERT INTO purchase_order (user_id) VALUE (?)";

    private static final String FIND_UNUSED_USER_BONUSES = "SELECT id, bonus_type, amount, status_use, user_id WHERE status_use = false AND user_id = ?";
    private static final String FIND_UNUSED_DISCOUNT_BONUS = "SELECT id, bonus_type, amount, status_use, user_id WHERE status_use = false AND user_id = ?" +
            " AND bonus_type = 'DISCOUNT'";
    private static final String FIND_UNUSED_FREE_TRACKS_BONUS = "SELECT id, bonus_type, amount, status_use, user_id WHERE status_use = false AND user_id = ?" +
            " AND bonus_type = 'FREE_TRACKS'";

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


    @Override
    public Optional<Bonus> getById(Long id) throws DaoException {
        return executeForSingleResult(UPDATE_CURRENT_ORDER, id);
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
