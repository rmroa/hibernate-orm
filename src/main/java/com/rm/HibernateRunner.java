package com.rm;

import com.rm.entity.UserChat;
import com.rm.util.HibernateUtil;
import com.rm.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.sql.SQLException;

public class HibernateRunner {

    @Transactional
    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            TestDataImporter.importData(sessionFactory);

            session.beginTransaction();

            UserChat userChat = session.find(UserChat.class, 1L);

//            Model model = session.find(Model.class, 1L);
//            model.setPrice(new BigDecimal("1090.100"));

            session.getTransaction().commit();
        }
    }
}
