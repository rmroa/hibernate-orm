package com.rm;

import com.rm.entity.Birthday;
import com.rm.entity.Gender;
import com.rm.entity.Role;
import com.rm.entity.User;
import com.rm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {

    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) throws SQLException {

        User user = User.builder()
                .id(20L)
                .firstName("FirtName")
                .lastName("LastName")
                .birthday(new Birthday(LocalDate.of(2000, 1, 20)))
                .image("")
                .country("Belarus")
                .city("Brest")
                .phone("375292901210")
                .email("firstname@mail.ru")
                .password("password")
//                    .info("{\n" +
//                            "      \"name\": \"FirstName\",\n" +
//                            "      \"id\": 25\n" +
//                            "    }")
                .role(Role.CUSTOMER)
                .gender(Gender.MALE)
                .build();
        log.info("User entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session sessionOne = sessionFactory.openSession();
            try (sessionOne) {
                Transaction transaction = sessionOne.beginTransaction();
                log.trace("Transaction is created, {}", transaction);

                sessionOne.saveOrUpdate(user);
                log.trace("User is in transient state: {}, session {}", user, sessionOne);

                sessionOne.getTransaction().commit();
            }
            log.warn("User is in detached state: {}, session is closed {}", user, sessionOne);
        } catch (Exception exception) {
            log.error("Exception occurred", exception);
            throw exception;
        }
    }
}