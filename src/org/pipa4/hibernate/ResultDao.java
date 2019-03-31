package org.pipa4.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pipa4.models.User;

import java.util.List;

public class ResultDao {

    public void saveUser(User user) {
        Session session = HibernateSessionFactoryUtil
                .getSessionFactory(User.class)
                .openSession();
        Transaction tx1 = session
                .beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public Object findById(Class objectClass, String id) {
        return HibernateSessionFactoryUtil.getSessionFactory(objectClass).openSession().get(objectClass, id);
    }

    /*
    public List<User> findAll() {
        List<User> users = (List<User>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }*/

}