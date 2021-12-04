package com.rm;

import com.rm.entity.Birthday;
import com.rm.entity.Gender;
import com.rm.entity.Role;
import com.rm.entity.User;
import com.rm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {

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

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session sessionOne = sessionFactory.openSession()) {
            sessionOne.beginTransaction();

            sessionOne.saveOrUpdate(user);

            sessionOne.getTransaction().commit();
        }

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session sessionTwo = sessionFactory.openSession()) {
            sessionTwo.beginTransaction();
            user.setFirstName("SetFirstName");

//            sessionTwo.delete(user);

//            sessionTwo.refresh(user);
//            User freshUserOne = sessionTwo.get(User.class, user.getId());
//            user.setFirstName(freshUserOne.getFirstName());
//            user.setLastName(freshUserOne.getLastName());

            sessionTwo.merge(user);
            User freshUserTwo = sessionTwo.get(User.class, user.getId());
            freshUserTwo.setFirstName(user.getFirstName());
            freshUserTwo.setLastName(user.getLastName());

            sessionTwo.getTransaction().commit();
        }
    }
}
