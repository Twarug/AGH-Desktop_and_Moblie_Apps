package dev.twardosz.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    private static final SessionFactory sessionFactory;
    private static Session session;

    private static Transaction currentTransaction;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        if (session == null || !session.isOpen()) {
            session = sessionFactory.openSession();
        }

        if (currentTransaction == null) {
            currentTransaction = session.beginTransaction();
        }

        return session;
    }

    public static void commit() {
        if (currentTransaction != null) {
            currentTransaction.commit();
            currentTransaction = null;
        }
    }

    public static void shutdown() {
        if (session != null) {
            commit();
            session.close();
            session = null;
        }
        getSessionFactory().close();
    }
}
