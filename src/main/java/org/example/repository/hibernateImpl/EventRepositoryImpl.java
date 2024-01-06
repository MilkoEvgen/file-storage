package org.example.repository.hibernateImpl;

import org.example.model.Event;
import org.example.model.File;
import org.example.model.User;
import org.example.repository.EventRepository;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class EventRepositoryImpl implements EventRepository {

    @Override
    public Event create(Event event) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            User user = session.get(User.class, event.getUser().getId());
            File file = session.get(File.class, event.getFile().getId());
            event.setUser(user);
            event.setFile(file);
            session.persist(event);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
        return event;
    }


    @Override
    public Event getById(Integer integer) {
        Event event;
        try (Session session = HibernateUtil.getSession()) {
            Query<Event> query = session.createQuery("FROM Event e LEFT JOIN FETCH e.user LEFT JOIN FETCH e.file WHERE e.id = :id", Event.class);
            query.setParameter("id", integer);
            event = query.getSingleResult();
        }
        return event;
    }

    @Override
    public List<Event> getAll() {
        List<Event> events;
        try (Session session = HibernateUtil.getSession()) {
            Query<Event> query = session.createQuery("FROM Event", Event.class);
            events = query.getResultList();
        }
        return events;
    }

    @Override
    public Event update(Event event) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.merge(event);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                throw e;
            }
        }
        return event;
    }

    @Override
    public boolean delete(Integer integer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            Event event = Event.builder().id(integer).build();
            session.remove(event);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                throw e;
            }
            return false;
        }
        return true;
    }

    @Override
    public List<Event> getEventsByUserId(Integer id) {
        List<Event> events;
        try (Session session = HibernateUtil.getSession()) {
            Query<Event> query = session.createQuery("FROM Event e LEFT JOIN FETCH e.user LEFT JOIN FETCH e.file WHERE e.user.id = :id", Event.class);
            query.setParameter("id", id);
            events = query.getResultList();
        }
        return events;
    }
}
