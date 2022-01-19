package com.rm;

import com.rm.entity.Model;
import com.rm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.util.List;

public class HibernateRunner {

    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            List<Model> models = session.createQuery("select m from Model m", Model.class)
                    .list();
            models.forEach(model -> System.out.println(model.getOrders().size()));
            models.forEach(model -> System.out.println(model.getManufacturer().getBrand()));

            session.getTransaction().commit();
        }
    }
}
