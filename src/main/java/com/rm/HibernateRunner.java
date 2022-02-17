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

//            session.createQuery("select m from Model m", Model.class)
//                    .setLockMode(LockModeType.PESSIMISTIC_READ)
//                    .setHint("javax.persistence.lock.timeout", 5000)
//                    .list();

            TestDataImporter.importData(sessionFactory);

            Model model = session.find(Model.class, 1L, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
            model.setPrice(new BigDecimal("1090.100"));

            Model model1 = session1.find(Model.class, 1L);
            model1.setPrice(new BigDecimal("100.100"));

            session.getTransaction().commit();
            session1.getTransaction().commit();
        }
    }
}
