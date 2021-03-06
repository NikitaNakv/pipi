package org.pipa4.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.pipa4.models.Result;

import java.util.List;

public class ResultDao {
    private Session session;
    private boolean sessionInUse;

    public ResultDao(){
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
                .getSessionResultFactory()
                .openSession();
        sessionInUse = true;
        return session;
    }

    public void setPoint(Result result) {
        Session session = getSession();
        Transaction tx1 = session
                .beginTransaction();
        session.save(result);
        tx1.commit();
        session.close();
        sessionInUse = false;
    }

    public List<Result> findAllByName(String user) {
        Session session = getSession();

        Criteria criteria = session.createCriteria(Result.class);

        List<Result> list = criteria.add(Restrictions.eq("fok", user)).list();

        session.close();
        sessionInUse = false;

        return list;
    }
}