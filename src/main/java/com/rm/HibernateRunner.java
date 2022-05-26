package com.rm;

import com.rm.dao.ModelRepository;
import com.rm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.lang.reflect.Proxy;
import java.sql.SQLException;

public class HibernateRunner {

    @Transactional
    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

            session.beginTransaction();

            ModelRepository modelRepository = new ModelRepository(session);
            modelRepository.findById(1L).ifPresent(System.out::println);

            session.getTransaction().commit();
        }
    }
}
