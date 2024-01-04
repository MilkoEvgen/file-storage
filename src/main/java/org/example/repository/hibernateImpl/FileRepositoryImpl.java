package org.example.repository.hibernateImpl;

import org.example.model.File;
import org.example.repository.FileRepository;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FileRepositoryImpl implements FileRepository {
    @Override
    public File create(File file) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(file);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            throw e;
        }
        return file;
    }


    @Override
    public File getById(Integer integer) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(File.class, integer);
        }
    }

    @Override
    public File update(File file) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.merge(file);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
                throw e;
            }
        }
        return file;
    }

    @Override
    public boolean delete(Integer integer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            File file = File.builder().id(integer).build();
            session.remove(file);
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
