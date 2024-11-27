package com.example.projetjee;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Charger la configuration Hibernate
            System.out.println("Tentative de chargement de la configuration Hibernate...");
            Configuration configuration = new Configuration();
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();
            System.out.println("Configuration Hibernate chargée avec succès.");
        } catch (HibernateException e) {
            System.err.println("Erreur d'initialisation de la SessionFactory : " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError("Initialisation de la SessionFactory échouée");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
