package service;

import config.HibernateConfig;
import model.Comic;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ServicioComic {

    public void insertComic(Comic comic) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(comic);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro inserindo comic: " + e.getMessage());
        }
    }

    public List<Comic> readComics() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("SELECT DISTINCT c FROM Comic c", Comic.class).list();
        } catch (Exception e) {
            System.out.println("Erro lendo comics: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void deleteComic(Comic comic) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(comic);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro borrando comic: " + e.getMessage());
        }
    }
}
