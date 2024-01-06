package org.example.repository.hibernateImpl;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User create(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(user);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            throw e;
        }
        return user;
    }

    @Override
    public User getById(Integer id) {
        User user;
        try (Session session = HibernateUtil.getSession()) {
            Query<User> query = session.createQuery("FROM User u LEFT JOIN FETCH u.events WHERE u.id = :id", User.class);
            query.setParameter("id", id);
            user = query.getSingleResult();
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users;
        try (Session session = HibernateUtil.getSession()) {
            Query<User> query = session.createQuery("FROM User u LEFT JOIN FETCH u.events", User.class);
            users = query.getResultList();
        }
        return users;
    }

    @Override
    public User update(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.merge(user);
            Query<User> query = session.createQuery("FROM User u LEFT JOIN FETCH u.events WHERE u.id = :id", User.class);
            query.setParameter("id", user.getId());
            user = query.getSingleResult();
            transaction.commit();
        } catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
                throw e;
            }
        }
        return user;
    }

    @Override
    public boolean delete(Integer integer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            User user = User.builder().id(integer).build();
            Query query = session.createQuery("DELETE File f WHERE f.id IN " +
                    "(SELECT e.file.id FROM Event e WHERE e.user.id = :userId)");
            query.setParameter("userId", integer);
            query.executeUpdate();
            session.remove(user);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
                throw e;
            }
            return false;
        }
        return true;
    }
}
