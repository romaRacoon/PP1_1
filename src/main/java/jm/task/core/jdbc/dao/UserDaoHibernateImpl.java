package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()){
            String command = "CREATE TABLE IF NOT EXISTS Users(Id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "Name VARCHAR(80), LastName VARCHAR(80), Age TINYINT);";
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(command).addEntity(User.class);

            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Не удалось создать таблицу");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String command = "DROP TABLE IF EXISTS Users";

            Query query = session.createSQLQuery(command).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Не удалось удалить таблицу");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
