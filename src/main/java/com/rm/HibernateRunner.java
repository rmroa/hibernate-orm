package com.rm;

import com.rm.dao.ManufacturerRepository;
import com.rm.dao.ModelRepository;
import com.rm.dao.VehicleTypeRepository;
import com.rm.dto.ModelCreateDto;
import com.rm.entity.DriveUnit;
import com.rm.entity.EngineType;
import com.rm.entity.Transmission;
import com.rm.interceptor.TransactionInterceptor;
import com.rm.mapper.DiscountReadMapper;
import com.rm.mapper.ManufacturerReadMapper;
import com.rm.mapper.ModelCreateMapper;
import com.rm.mapper.ModelReadMapper;
import com.rm.mapper.VehicleTypeReadMapper;
import com.rm.service.ModelService;
import com.rm.util.HibernateUtil;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {

    @Transactional
    public static void main(String[] args) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

//            session.beginTransaction();

            ManufacturerRepository manufacturerRepository = new ManufacturerRepository(session);
            VehicleTypeRepository vehicleTypeRepository = new VehicleTypeRepository(session);

            ManufacturerReadMapper manufacturerReadMapper = new ManufacturerReadMapper();
            VehicleTypeReadMapper vehicleTypeReadMapper = new VehicleTypeReadMapper();
            DiscountReadMapper discountReadMapper = new DiscountReadMapper();
            ModelCreateMapper modelCreateMapper = new ModelCreateMapper(manufacturerRepository, vehicleTypeRepository);

            ModelReadMapper modelReadMapper = new ModelReadMapper(manufacturerReadMapper, vehicleTypeReadMapper, discountReadMapper);
            ModelRepository modelRepository = new ModelRepository(session);
//            ModelService modelService = new ModelService(modelRepository, modelReadMapper, modelCreateMapper);
            TransactionInterceptor transactionInterceptor = new TransactionInterceptor(sessionFactory);

            ModelService modelService = new ByteBuddy()
                    .subclass(ModelService.class)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(transactionInterceptor))
                    .make()
                    .load(ModelService.class.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor(ModelRepository.class, ModelReadMapper.class, ModelCreateMapper.class)
                    .newInstance(modelRepository, modelReadMapper, modelCreateMapper);

//            modelService.findById(1L).ifPresent(System.out::println);

            ModelCreateDto modelCreateDto = new ModelCreateDto(
                    "NewCreateModel",
                    3,
                    LocalDate.of(2000, 1, 1),
                    3L,
                    Transmission.AUTOMATIC,
                    DriveUnit.FRONT,
                    EngineType.PETROL,
                    100L,
                    new BigDecimal("27.000")
            );
            modelService.create(modelCreateDto);

//            session.getTransaction().commit();
        }
    }
}