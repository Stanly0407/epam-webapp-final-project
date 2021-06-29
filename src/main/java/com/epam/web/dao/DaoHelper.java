package com.epam.web.dao;

import com.epam.web.connection.ConnectionPool;
import com.epam.web.connection.ProxyConnection;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.*;

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
        TrackRowMapper trackRowMapper = new TrackRowMapper();
        return new TrackDao(proxyConnection, trackRowMapper);
    }

    public MusicCollectionDao createMusicCollectionDao() {
        MusicCollectionRowMapper musicCollectionDtoRowMapper = new MusicCollectionRowMapper();
        return new MusicCollectionDao(proxyConnection, musicCollectionDtoRowMapper);
    }

    public ArtistDao createArtistDao() {
        ArtistRowMapper artistRowMapper = new ArtistRowMapper();
        return new ArtistDao(proxyConnection, artistRowMapper);
    }

    public OrderDao createOrderDao() {
        OrderRowMapper orderRowMapper = new OrderRowMapper();
        return new OrderDao(proxyConnection, orderRowMapper);
    }

    public CommentDao createCommentDao() {
        CommentRowMapper commentRowMapper = new CommentRowMapper();
        return new CommentDao(proxyConnection, commentRowMapper);
    }

    public BonusDao createBonusDao() {
        BonusRowMapper bonusRowMapper = new BonusRowMapper();
        return new BonusDao(proxyConnection, bonusRowMapper);
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
