package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction= null;

        try (Session session = getSessionFactory().openSession()){
            String command = "CREATE TABLE IF NOT EXISTS Users(Id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "Name VARCHAR(80), LastName VARCHAR(80), Age TINYINT);";
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(command).addEntity(User.class);

            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Не удалось создать таблицу");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String command = "DROP TABLE IF EXISTS Users";

            Query query = session.createSQLQuery(command).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Не удалось удалить таблицу");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User addeduser = new User(name, lastName, age);
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(addeduser);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Не удалось сохранить пользователя в бд");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Не удалось удалить пользователя из бд");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        CriteriaBuilder criteriaBuilder = getSessionFactory().openSession().getCriteriaBuilder();
        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
        userCriteriaQuery.from(User.class);
        List<User> users = getSessionFactory().openSession().createQuery(userCriteriaQuery)
                .getResultList();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final List<?> instances = session.createCriteria(User.class).list();
        for (Object obj : instances) {
            session.delete(obj);
        }
        session.getTransaction().commit();
    }
}
