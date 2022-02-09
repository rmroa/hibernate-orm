package com.rm;

import com.rm.entity.Model;
import com.rm.util.HibernateUtil;
import com.rm.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.SQLException;

public class HibernateRunner {

    @Transactional
    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            Session session1 = sessionFactory.openSession();

            session.beginTransaction();
            session1.beginTransaction();

            TestDataImporter.importData(sessionFactory);

            Model model = session.find(Model.class, 1L, LockModeType.OPTIMISTIC);
            model.setPrice(new BigDecimal("100.100"));

            Model model1 = session1.find(Model.class, 1L, LockModeType.OPTIMISTIC);
            model1.setPrice(new BigDecimal("100.100"));

            session.getTransaction().commit();
            session1.getTransaction().commit();
        }
    }
}
