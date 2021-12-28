package com.rm;

import com.rm.entity.Birthday;
import com.rm.entity.Discount;
import com.rm.entity.DiscountInfo;
import com.rm.entity.DriveUnit;
import com.rm.entity.EngineType;
import com.rm.entity.Gender;
import com.rm.entity.Manufacturer;
import com.rm.entity.Model;
import com.rm.entity.Order;
import com.rm.entity.PersonalInfo;
import com.rm.entity.Role;
import com.rm.entity.Transmission;
import com.rm.entity.User;
import com.rm.entity.VehicleType;
import com.rm.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) throws SQLException {

        User user = User.builder()
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
                .role(Role.CUSTOMER)
                .build();

        Manufacturer manufacturer = Manufacturer.builder()
                .brand("SaveBrand")
                .country("SaveCountry")
                .build();

        VehicleType vehicleType = VehicleType.builder()
                .type("SaveType")
                .build();

        Model model = Model.builder()
                .model("SaveModel")
                .manufacturer(manufacturer)
                .productionYear(LocalDate.of(1990, 10, 13))
                .vehicleType(vehicleType)
                .transmission(Transmission.MANUAL)
                .driveUnit(DriveUnit.FRONT)
                .engineType(EngineType.DIESEL)
                .currentMileage(100L)
                .price(new BigDecimal("77.700"))
                .build();

        Discount discount = Discount.builder()
                .discountInfo(DiscountInfo.builder()
                        .amountOfDiscount(19L)
                        .startDate(LocalDate.of(2021, 12, 20))
                        .endDate(LocalDate.of(2021, 12, 29))
                        .build())
                .build();

//        Order order = Order.builder()
//                .user(user)
//                .model(model)
//                .discount(discount)
//                .date(LocalDateTime.now())
//                .cost(new BigDecimal("62.937"))
//                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session sessionOne = sessionFactory.openSession();
            try (sessionOne) {
                Transaction transaction = sessionOne.beginTransaction();

                Order order2 = sessionOne.get(Order.class, 1);
                User user1 = order2.getUser();
//                Long id = user1.getId();
                String firstName = user1.getPersonalInfo().getFirstName();

//                sessionOne.save(user);
//                sessionOne.save(manufacturer);
//                sessionOne.save(vehicleType);
//                sessionOne.save(model);
//                sessionOne.save(discount);
//                sessionOne.save(order);
                Object object = Hibernate.unproxy(user1);

                sessionOne.getTransaction().commit();
            }
        }
    }
}