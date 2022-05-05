package com.rm.util;

import com.rm.entity.Chat;
import com.rm.entity.Discount;
import com.rm.entity.DiscountInfo;
import com.rm.entity.Manufacturer;
import com.rm.entity.Model;
import com.rm.entity.PersonalInfo;
import com.rm.entity.User;
import com.rm.entity.UserChat;
import com.rm.entity.VehicleType;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Manufacturer bmw = saveManufacturer(session, "bmw");
        Manufacturer audi = saveManufacturer(session, "audi");
        Manufacturer mercedes = saveManufacturer(session, "mercedes");

        VehicleType crossover = saveVehicleType(session, "crossover");
        VehicleType hatchback = saveVehicleType(session, "hatchback");
        VehicleType coupe = saveVehicleType(session, "coupe");

        Discount five = saveDiscount(session, 5);
        Discount fifteen = saveDiscount(session, 15);
        Discount twentyFive = saveDiscount(session, 25);

        Model x6 = saveModel(session, crossover, bmw, "x6", new BigDecimal("70.700"), LocalDate.of(2019, 1, 12), five);
        Model a6 = saveModel(session, coupe, audi, "a6", new BigDecimal("50.100"), LocalDate.of(2018, 3, 12), fifteen);
        Model amgGt = saveModel(session, hatchback, mercedes, "amgGt", new BigDecimal("66.300"), LocalDate.of(201, 9, 13), twentyFive);

        User userFirst = saveUser(session, "Monika", "Geller");
        User userSecond = saveUser(session, "Rachel", "Green");
        User userThird = saveUser(session, "Ross", "Geller");

        Chat firstChat = saveChat(session, "firstChat");
        Chat secondChat = saveChat(session, "secondChat");
        Chat thirdChat = saveChat(session, "thirdChat");

        saveUserChat(session, firstChat, userFirst);
        saveUserChat(session, secondChat, userSecond, userFirst);
        saveUserChat(session, thirdChat, userThird, userSecond, userFirst);

        session.getTransaction().commit();
    }

    private User saveUser(Session session, String firstname, String lastname) {
        User user = User.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstName(firstname)
                        .lastName(lastname)
                        .build())
                .build();
        session.save(user);
        return user;
    }

    private Chat saveChat(Session session, String name) {
        Chat chat = Chat.builder()
                .name(name)
                .build();
        session.save(chat);
        return chat;
    }

    private void saveUserChat(Session session, Chat chat, User... users) {
        Arrays.stream(users)
                .map(user -> UserChat.builder()
                        .user(user)
                        .chat(chat)
                        .build())
                .forEach(session::save);
    }

    private Discount saveDiscount(Session session, long amountOfDiscount) {
        Discount discount = Discount.builder()
                .discountInfo(DiscountInfo.builder()
                        .amountOfDiscount(amountOfDiscount)
                        .build())
                .build();
        session.save(discount);
        return discount;
    }

    private VehicleType saveVehicleType(Session session, String name) {
        VehicleType vehicleType = VehicleType.builder()
                .type(name)
                .build();
        session.save(vehicleType);
        return vehicleType;
    }

    private Model saveModel(Session session, VehicleType vehicleType, Manufacturer brand, String name, BigDecimal price, LocalDate productionDate, Discount discount) {
        Model model = Model.builder()
                .vehicleType(vehicleType)
                .manufacturer(brand)
                .model(name)
                .price(price)
                .productionYear(productionDate)
                .discount(discount)
                .build();
        session.save(model);
        return model;
    }

    private Manufacturer saveManufacturer(Session session, String brand) {
        Manufacturer manufacturer = Manufacturer.builder()
                .brand(brand)
                .build();
        session.save(manufacturer);
        return manufacturer;
    }
}
