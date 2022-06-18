package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();

    public void createUsersTable() {
        userDaoJDBC.createUsersTable();
    }

    public void dropUsersTable() {
        userDaoJDBC.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDaoJDBC.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        userDaoJDBC.removeUserById(id);
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
        userDaoJDBC.cleanUsersTable();
    }
}
