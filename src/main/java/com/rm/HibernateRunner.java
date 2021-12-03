package com.rm;

import com.rm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;

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
//        configuration.addAttributeConverter(new BirthdayConverter(), true); // аналог @Convert(converter = BirthdayConverter.class)
//        configuration.registerTypeOverride(new JsonBinaryType()); // регистрация JsonBinaryType для поля "private String info";
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

//            User user = User.builder()
//                    .id(20L)
//                    .firstName("FirstName")
//                    .lastName("LastName")
//                    .birthday(new Birthday(LocalDate.of(2000, 1, 20)))
//                    .image("")
//                    .country("Belarus")
//                    .city("Brest")
//                    .phone("375292901210")
//                    .email("firstname@mail.ru")
//                    .password("password")
////                    .info("{\n" +
////                            "      \"name\": \"FirstName\",\n" +
////                            "      \"id\": 25\n" +
////                            "    }")
//                    .role(Role.CUSTOMER)
//                    .gender(Gender.MALE)
//                    .build();
//            session.saveOrUpdate(user);
            User userOne = session.get(User.class, 1L);

            session.getTransaction().commit();
        }
    }
}
