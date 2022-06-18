package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private boolean ifTableExists(Connection connection, String tableName) {

    }

    public void createUsersTable() {
        String command = "CREATE TABLE IF NOT EXISTS Users(Id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "Name VARCHAR(80), LastName VARCHAR(80), Age TINYINT);";
        Statement statement = null;
        try {
            statement = Util.getConnection().createStatement();
            statement.executeUpdate(command);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String command = "DROP TABLE Users;";
        if (ifTableExists(Util.getConnection(), "Users")) {
            try {
                Statement statement = Util.getConnection().createStatement();
                statement.executeUpdate(command);
            } catch (SQLException e) {
                System.out.println(1111);
                throw new RuntimeException(e);
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String command = "INSERT INTO Users(Name, LastName, Age) " +
                String.format("VALUES('%s', '%s', '%s');", name, lastName, age);
        try (Connection connection = Util.getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate(command);
            System.out.println(String.format("User с именем – %s добавлен в базу данных", name));
        } catch (SQLException e) {
            System.out.println("Не удалось сохранить пользователя в бд");
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String command = String.format("DELETE FROM Users WHERE Id = %s;", id - 1);
        try (Connection connection = Util.getConnection()){

        } catch (SQLException e) {
            System.out.println("Не удалось удалить пользователя по id");
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String command = "SELECT * FROM Users;";
        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
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
        try (Connection connection = Util.getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate(command);
        } catch (SQLException e) {
            System.out.println("Не удалось удалить данные из таблицы");
            throw new RuntimeException(e);
        }
    }
}
