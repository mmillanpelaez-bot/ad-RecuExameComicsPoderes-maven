package service;

import config.HibernateConfig;
import model.Autor;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServicioAutor {

    public void updateAutor(Autor autor) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(autor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro actualizando autor: " + e.getMessage());
        }
    }

    public void deleteAutor(Autor autor) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(autor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro borrando autor: " + e.getMessage());
        }
    }
}
