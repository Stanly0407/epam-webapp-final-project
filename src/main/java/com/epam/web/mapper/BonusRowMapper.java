package com.epam.web.mapper;

import com.epam.web.entities.Bonus;
import com.epam.web.entities.BonusType;
import com.epam.web.entities.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BonusRowMapper implements RowMapper<Bonus> {

    @Override
    public Bonus map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Bonus.ID);
        String type = resultSet.getString(Bonus.BONUS_TYPE);
        BonusType bonusType = BonusType.valueOf(type);
        int amount = resultSet.getInt(Bonus.AMOUNT);
        boolean statusUse = resultSet.getBoolean(Bonus.STATUS_USE);
        Long userId = resultSet.getLong(Order.USER_ID);
        return new Bonus(id, bonusType, amount, statusUse, userId);
    }
}
