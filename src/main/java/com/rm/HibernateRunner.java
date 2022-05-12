package com.rm;

import com.rm.entity.Model;
import com.rm.util.HibernateUtil;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import javax.transaction.Transactional;
import java.sql.Date;
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

            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                AuditReader auditReader = AuditReaderFactory.get(session2);
                Model oldModel = auditReader.find(Model.class, 1L, new Date(1652298315674L));
                session2.replicate(oldModel, ReplicationMode.OVERWRITE);

                System.out.println();

                session2.getTransaction().commit();
            }
        }
    }
}
