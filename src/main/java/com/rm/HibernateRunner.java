package com.rm;

import com.rm.entity.Model;
import com.rm.util.HibernateUtil;
import com.rm.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.SQLException;

public class HibernateRunner {

    @Transactional
    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            TestDataImporter.importData(sessionFactory);

//            session.setDefaultReadOnly(true);
            session.beginTransaction();

            session.createNativeQuery("SET TRANSACTION READ ONLY;").executeUpdate();

//            session.createQuery("select m from Model m", Model.class)
//                    .setLockMode(LockModeType.PESSIMISTIC_READ)
//                    .setHint("javax.persistence.lock.timeout", 5000)
//                    .setReadOnly(true)
//                    .list();


            Model model = session.find(Model.class, 1L);
            model.setPrice(new BigDecimal("1090.100"));

            session.getTransaction().commit();
        }
    }
}
