package com.rm.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        configuration.configure();
        return configuration.buildSessionFactory();
    }

    public static Configuration buildConfiguration() {
//        configuration.addAnnotatedClass(User.class); // аналог <mapping class="com.rm.entity.User"/>
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy()); // аналог @Column(name = "...")
//        configuration.addAttributeConverter(new BirthdayConverter(), true); // аналог @Convert(converter = BirthdayConverter.class)
//        configuration.registerTypeOverride(new JsonBinaryType()); // регистрация JsonBinaryType для поля "private String info";
        return new Configuration();
    }
}
