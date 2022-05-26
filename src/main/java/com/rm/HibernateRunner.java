package com.rm;

import com.rm.dao.ModelRepository;
import com.rm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.sql.SQLException;

public class HibernateRunner {

    @Transactional
    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                ModelRepository modelRepository = new ModelRepository(sessionFactory);
                modelRepository.findById(1L).ifPresent(System.out::println);

                session.getTransaction().commit();
            }
        }
    }
}