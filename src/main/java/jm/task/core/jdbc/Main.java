package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        Util util = new Util();
        util.getConnection();
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Роман", "Артеменков", (byte) 19);
        userService.saveUser("Дмитрий", "Задорнов", (byte) 20);
        userService.saveUser("Сергей", "Огородов", (byte) 22);
        List<User> users = userService.getAllUsers();
        System.out.println(users);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
