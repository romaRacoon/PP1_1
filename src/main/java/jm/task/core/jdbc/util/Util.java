package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String NAME = "root";
    private static final String PASSWORD = "rickandmortyroman1M";
    private static final String URL = "jdbc:mysql://localhost:3306/testwirhutf";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
