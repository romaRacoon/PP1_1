package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.boot.registry.StandardServiceRegistry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String command = "CREATE TABLE IF NOT EXISTS Users(Id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "Name VARCHAR(80), LastName VARCHAR(80), Age TINYINT);";

        try {
            if (connection.getMetaData().getTables(null, null, "Users", null).next()) {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(command);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String command = "DROP TABLE Users;";
        try {
            if (connection.getMetaData().getTables(null, null, "Users", null).next() == false) {
                try (Statement statement = connection.createStatement()){
                    statement.executeUpdate(command);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String command = "INSERT INTO Users(Name, LastName, Age) VALUES(?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println(String.format("User с именем – %s добавлен в базу данных", name));
        } catch (SQLException e) {
            System.out.println("Не удалось сохранить пользователя в бд");
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String command = "DELETE FROM Users WHERE Id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Не удалось удалить пользователя по id");
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String command = "SELECT * FROM Users;";
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("Id");
                String name = resultSet.getString("Name");
                String lastName = resultSet.getString("LastName");
                int age = resultSet.getByte("Age");

                User addedUser = new User(name, lastName, (byte) age);
                addedUser.setId(id);
                users.add(addedUser);
            }
        } catch (SQLException e) {
            System.out.println("Не удалось получить список пользователей");
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        String command = "TRUNCATE Table Users";
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(command);
        } catch (SQLException e) {
            System.out.println("Не удалось удалить данные из таблицы");
            throw new RuntimeException(e);
        }
    }
}
