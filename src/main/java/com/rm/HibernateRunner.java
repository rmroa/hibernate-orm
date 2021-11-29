package com.rm;

import com.rm.entity.Gender;
import com.rm.entity.Role;
import com.rm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) throws SQLException {
//        SessionFactory
//        BlockingDeque<Connection> pool = null;
//        Connection connection = pool.take();

//        Session
//        Connection connection = DriverManager
//                .getConnection("db.url", "db.username", "db.password");


        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(User.class); // аналог <mapping class="com.rm.entity.User"/>
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy()); // аналог @Column(name = "...")
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .firstName("FirstName")
                    .lastName("LastName")
                    .birthday(LocalDate.of(2000, 1, 20))
                    .image("")
                    .country("Belarus")
                    .city("Brest")
                    .phone("+375292901210")
                    .email("firstname@mail.ru")
                    .password("password")
                    .role(Role.valueOf("USER"))
                    .gender(Gender.valueOf("MALE"))
                    .build();
            session.save(user);

            session.getTransaction().commit();
        }
    }
}
