package service;

import config.HibernateConfig;
import model.PoderUnico;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServicioPoderUnico {

    public void insertPoderUnico(PoderUnico poderUnico) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(poderUnico);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro inserindo poder unico: " + e.getMessage());
        }
    }

    public void deletePoderUnico(PoderUnico poderUnico) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(poderUnico);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro borrando poder unico: " + e.getMessage());
        }
    }
}
