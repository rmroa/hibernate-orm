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

            Model model = null;

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                model = session.find(Model.class, 1L);
                model.getManufacturer().getBrand();
                model.getOrders().size();
                Model model1 = session.find(Model.class, 1L);

                session.getTransaction().commit();
            }

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Model model2 = session.find(Model.class, 1L);
                model2.getManufacturer().getBrand();
                model2.getOrders().size();

                session.getTransaction().commit();
            }
        }
    }
}