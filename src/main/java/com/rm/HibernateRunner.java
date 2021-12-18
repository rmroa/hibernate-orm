package com.rm;

import com.rm.entity.Birthday;
import com.rm.entity.Gender;
import com.rm.entity.PersonalInfo;
import com.rm.entity.Role;
import com.rm.entity.User;
import com.rm.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) throws SQLException {

        User user = User.builder()
                .id(21L)
                .personalInfo(PersonalInfo.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .birthday(new Birthday(LocalDate.of(2000, 1, 20)))
                        .image("")
                        .country("Belarus")
                        .city("Brest")
                        .phone("375292901210")
                        .email("firstname@mail.ru")
                        .password("password")
                        .gender(Gender.MALE)
                        .build())
//                    .info("{\n" +
//                            "      \"name\": \"FirstName\",\n" +
//                            "      \"id\": 25\n" +
//                            "    }")
                .role(Role.CUSTOMER)
                .build();
        log.info("User entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session sessionOne = sessionFactory.openSession();
            try (sessionOne) {
                Transaction transaction = sessionOne.beginTransaction();
                log.trace("Transaction is created, {}", transaction);

                sessionOne.saveOrUpdate(user);
                log.trace("User is in persistent state: {}, session {}", user, sessionOne);

                sessionOne.getTransaction().commit();
            }
            log.warn("User is in detached state: {}, session is closed {}", user, sessionOne);
        } catch (Exception exception) {
            log.error("Exception occurred", exception);
            throw exception;
        }
    }
}