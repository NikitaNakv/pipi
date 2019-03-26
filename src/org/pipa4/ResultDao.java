package org.pipa4;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ResultDao {

    public void save(Result result) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(result);
        tx1.commit();
        session.close();
    }

    public void delete(Result user) {
    }

}