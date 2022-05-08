package com.rm.util;

import com.rm.interceptor.GlobalInterceptor;
import com.rm.listener.AuditTableListener;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        configuration.configure();

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        registerListeners(sessionFactory);

        return sessionFactory;
    }

    private static void registerListeners(SessionFactory sessionFactory) {
        SessionFactoryImpl sessionFactoryimpl = sessionFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry eventListenerRegistry = sessionFactoryimpl.getServiceRegistry().getService(EventListenerRegistry.class);
        AuditTableListener auditTableListener = new AuditTableListener();
        eventListenerRegistry.appendListeners(EventType.PRE_INSERT, auditTableListener);
        eventListenerRegistry.appendListeners(EventType.PRE_DELETE, auditTableListener);
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(User.class); // аналог <mapping class="com.rm.entity.User"/>
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy()); // аналог @Column(name = "...")
//        configuration.addAttributeConverter(new BirthdayConverter(), true); // аналог @Convert(converter = BirthdayConverter.class)
//        configuration.registerTypeOverride(new JsonBinaryType()); // регистрация JsonBinaryType для поля "private String info";
        configuration.setInterceptor(new GlobalInterceptor());
        return configuration;
    }
}
