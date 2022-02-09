package com.rm;

import com.rm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.sql.SQLException;

public class HibernateRunner {

    @Transactional
    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.doWork(connection -> System.out.println(connection.getTransactionIsolation()));

//            try {
//                Transaction transaction = session.beginTransaction();
//
//                Model model1 = session.find(Model.class, 1L);
//                Model model2 = session.find(Model.class, 2L);
//
//                session.getTransaction().commit();
//            } catch (Exception exception) {
//                session.getTransaction().rollback();
//                throw exception;
//            }
        }
    }
}
