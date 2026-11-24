package service;

import config.HibernateConfig;
import model.Protagonista;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServicioProtagonista {

    public void updateProtagonista(Protagonista protagonista) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(protagonista);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro actualizando protagonista: " + e.getMessage());
        }
    }

    public void deleteProtagonista(Protagonista protagonista) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(protagonista);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro borrando protagonista: " + e.getMessage());
        }
    }
}
