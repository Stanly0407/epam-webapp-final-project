package com.epam.web;


import com.epam.web.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/music_wizard";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT id, name FROM USER";

    public static void main(String[] args) {
        List<User> users = null;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LOGIN_AND_PASSWORD);
            ResultSet resultSet = preparedStatement.executeQuery();
            users = new ArrayList<>();

            while (resultSet.next()) {

                User user = new User(resultSet.getLong("id"), resultSet.getString("name"));
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        for (User u : users) {
            System.out.println(u);
        }
    }

}
