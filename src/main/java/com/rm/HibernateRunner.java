package com.rm;

import com.rm.entity.Model;
import com.rm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.sql.SQLException;

public class HibernateRunner {

    @Transactional
    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
//            TestDataImporter.importData(sessionFactory);

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Model model = session.find(Model.class, 1L);

                session.getTransaction().commit();
            }
        }
    }
}
