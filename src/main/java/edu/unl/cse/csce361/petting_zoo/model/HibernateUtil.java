package edu.unl.cse.csce361.petting_zoo.model;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static Session session = null;

    public static Session getSession() {
        if (session == null) {
            SessionFactory sessionFactory = null;
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            try {
                sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
            } catch (Exception exception) {
                // sessionFactory didn't have the opportunity to destroy serviceRegistry like it should've
                StandardServiceRegistryBuilder.destroy(serviceRegistry);
                System.err.println("Failed to create session.  " + exception);
                System.exit(1);
            }
            session = sessionFactory.openSession();
        }
        return session;
    }

    public static void closeSession(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException hibernateException) {
                System.err.println("Failed to close session due to Hibernate problem.  " + hibernateException);
                System.err.println("Abandoning possibly-open session.");
            } finally {
                //noinspection UnusedAssignment
                session = null;
            }
        }
    }
}
