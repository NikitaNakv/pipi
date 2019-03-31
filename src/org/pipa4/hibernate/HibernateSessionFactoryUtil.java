package org.pipa4.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.pipa4.models.Result;
import org.pipa4.models.User;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionResultFactory;
    private static SessionFactory sessionUserFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionResultFactory() {
            if (sessionResultFactory == null) {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Result.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionResultFactory = configuration.buildSessionFactory(builder.build());
            }
            return sessionResultFactory;
    }

    public static SessionFactory getSessionUserFactory() {
        if (sessionUserFactory == null) {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(User.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionUserFactory = configuration.buildSessionFactory(builder.build());
        }
        return sessionUserFactory;
    }

}