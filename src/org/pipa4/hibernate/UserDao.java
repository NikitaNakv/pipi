package org.pipa4.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pipa4.models.User;

public class UserDao {
    private Session session;
    private boolean sessionInUse;

    public UserDao(){
        sessionInUse = false;
    }

    private Session getSession(){
        while (sessionInUse){
            try {
                Thread.sleep(500);
            } catch (Exception ignored) { }
        }
        if (session != null && session.isOpen()){
            session.close();
        }
        session = HibernateSessionFactoryUtil
                .getSessionUserFactory()
                .openSession();
        sessionInUse = true;
        return session;
    }

    public void saveUser(User user) {
        Session session = getSession();
        Transaction tx1 = session
                .beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
        sessionInUse = false;
    }

    public void updateUser(User user) {
        Session session = getSession();
        Transaction tx1 = session
                .beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
        sessionInUse = false;
    }

    public User findById(String id) {
        Session session = getSession();
        User user = session.get(User.class, id);
        session.close();
        sessionInUse = false;
        return user;
    }
}