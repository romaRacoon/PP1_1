package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.saveUser("Роман", "Артеменков", (byte) 19);
        userDao.saveUser("Дмитрий", "Задорнов", (byte) 20);
        userDao.saveUser("Сергей", "Огородов", (byte) 22);
        List<User> users = userDao.getAllUsers();
        System.out.println(users);
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
        Util.closeConnection();
    }
}
