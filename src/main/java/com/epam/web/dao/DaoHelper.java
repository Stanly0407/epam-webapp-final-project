package com.epam.web.dao;

import com.epam.web.connection.ConnectionPool;
import com.epam.web.connection.ProxyConnection;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.MusicCollectionDtoRowMapper;
import com.epam.web.mapper.TrackDtoRowMapper;
import com.epam.web.mapper.UserRowMapper;

import java.sql.SQLException;

public class DaoHelper implements AutoCloseable {

    private ProxyConnection proxyConnection;

    public DaoHelper(ConnectionPool connectionPool) {
        this.proxyConnection = connectionPool.getConnection();
    }

    public UserDao createUserDao() {
        UserRowMapper userRowMapper = new UserRowMapper();
        return new UserDao(proxyConnection, userRowMapper);
    }

    public TrackDao createTrackDao() {
        TrackDtoRowMapper trackRowMapper = new TrackDtoRowMapper();
        return new TrackDao(proxyConnection, trackRowMapper);
    }

    public MusicCollectionDao createMusicCollectionDao() {
        MusicCollectionDtoRowMapper musicCollectionDtoRowMapper = new MusicCollectionDtoRowMapper();
        return new MusicCollectionDao(proxyConnection, musicCollectionDtoRowMapper);
    }

    @Override
    public void close() {
        proxyConnection.close();
    }

    public void startTransaction() throws DaoException {
        try {
            proxyConnection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void endTransaction() throws DaoException {
        try {
            proxyConnection.commit();
            proxyConnection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
