package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private final String NAME = "root";
    private final String PASSWORD = "rickandmortyroman1M";
    private final String URL = "jdbc:mysql://localhost:3306/testwirhutf";

    private static Connection connection;
    private static Statement statement;

    private void createStatement() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void installConnection() {
        try {
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
