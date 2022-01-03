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
import com.rm.entity.Profile;
import com.rm.entity.Role;
import com.rm.entity.Transmission;
import com.rm.entity.User;
import com.rm.entity.VehicleType;
import com.rm.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

class HibernateRunnerTest {

    @Test
    void checkOneToOne() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .personalInfo(PersonalInfo.builder()
                            .firstName("NewName")
                            .lastName("NewLastName")
                            .birthday(new Birthday(LocalDate.of(2000, 1, 20)))
                            .image("")
                            .country("Belarus")
                            .city("Brest")
                            .phone("375297456312")
                            .email("newname@mail.ru")
                            .password("password")
                            .gender(Gender.MALE)
                            .build())
                    .role(Role.CUSTOMER)
                    .build();

            Profile profile = Profile.builder()
                    .street("NewStreet")
                    .language("ru")
                    .build();

            session.save(user);
            profile.setUser(user);

            session.getTransaction().commit();
        }
    }

    @Test
    void checkOrphanRemoval() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 1L);
            user.getOrders().removeIf(order -> order.getId().equals(1));

            session.getTransaction().commit();
        }
    }

    @Test
    void checkLazyInitialization() {
        User user = null;
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            user = session.get(User.class, 1L);
            System.out.println("");

            session.getTransaction().commit();
        }
        Set<Order> orders = user.getOrders();
        System.out.println(orders.size());
    }

    @Test
    void addOrderToNewUser() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = User.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstName("NewName")
                        .lastName("NewLastName")
                        .birthday(new Birthday(LocalDate.of(2000, 1, 20)))
                        .image("")
                        .country("Belarus")
                        .city("Brest")
                        .phone("375297456312")
                        .email("newname@mail.ru")
                        .password("password")
                        .gender(Gender.MALE)
                        .build())
                .role(Role.CUSTOMER)
                .build();

        Manufacturer manufacturer = Manufacturer.builder()
                .brand("NewBrand")
                .country("NewCountry")
                .build();

        VehicleType vehicleType = VehicleType.builder()
                .type("NewType")
                .build();

        Discount discount = Discount.builder()
                .discountInfo(DiscountInfo.builder()
                        .amountOfDiscount(19L)
                        .startDate(LocalDate.of(2021, 12, 20))
                        .endDate(LocalDate.of(2021, 12, 29))
                        .build())
                .build();

        Model model = Model.builder()
                .model("NewModel")
                .manufacturer(manufacturer)
                .productionYear(LocalDate.of(1990, 10, 13))
                .vehicleType(vehicleType)
                .transmission(Transmission.MANUAL)
                .driveUnit(DriveUnit.FRONT)
                .engineType(EngineType.DIESEL)
                .currentMileage(100L)
                .price(new BigDecimal("77.700"))
                .discount(discount)
                .build();

        Order order = Order.builder()
                .user(user)
                .model(model)
                .date(LocalDateTime.now())
                .cost(new BigDecimal("62.937"))
                .build();

        vehicleType.addModel(model);

        session.save(vehicleType);

        session.getTransaction().commit();
    }

    @Test
    void oneToMany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.get(User.class, 1L);
        System.out.println("");

        session.getTransaction().commit();
    }

//    @Test
//    void checkGetReflectionApi() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = preparedStatement.executeQuery();
//        resultSet.getLong("id");
//        resultSet.getString("first_name");
//        resultSet.getString("last_name");
//        resultSet.getDate("birthday").toLocalDate();
//        resultSet.getString("image");
//        resultSet.getString("country");
//        resultSet.getString("city");
//        resultSet.getString("phone");
//        resultSet.getString("email");
//        resultSet.getString("password");
//        Role.valueOf(resultSet.getString("role"));
//        Gender.valueOf(resultSet.getString("gender"));
//
//        Class<User> clazz = User.class;
//
//        Constructor<User> constructor = clazz.getConstructor();
//        User user = constructor.newInstance();
//        Field firstNameField = clazz.getDeclaredField("firstName");
//        firstNameField.setAccessible(true);
//        firstNameField.set(user, resultSet.getString("first_name"));
//    }

//    @Test
//    void checkReflectionApi() throws SQLException, IllegalAccessException {
//        User user = User.builder()
//                .firstName("FirstName")
//                .lastName("LastName")
//                .birthday(new Birthday(LocalDate.of(2000, 1, 20)))
//                .image("")
//                .country("Belarus")
//                .city("Brest")
//                .phone("375292901210")
//                .email("firstname@mail.ru")
//                .password("password")
//                .role(Role.valueOf("USER"))
//                .gender(Gender.valueOf("MALE"))
//                .build();
//
//        String sql = "" +
//                "INSERT INTO " +
//                "%s " +
//                "(%s) " +
//                "VALUES (%s)";
//
//        String tableName = ofNullable(user.getClass().getAnnotation(Table.class))
//                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
//                .orElse(user.getClass().getName());
//
//        Field[] declaredFields = user.getClass().getDeclaredFields();
//
//        String columnsNames = Arrays.stream(declaredFields)
//                .map(field -> ofNullable(field.getAnnotation(Column.class))
//                        .map(Column::name)
//                        .orElse(field.getName()))
//                .collect(joining(", "));
//
//        String columnValues = Arrays.stream(declaredFields)
//                .map(field -> "?")
//                .collect(joining(", "));
//
//        System.out.println(format(sql, tableName, columnsNames, columnValues));
//
//        Connection connection = null;
//        PreparedStatement preparedStatement = connection.prepareStatement(format(sql, tableName, columnsNames, columnValues));
//        for (Field declaredField : declaredFields) {
//            declaredField.setAccessible(true);
//            preparedStatement.setObject(1, declaredField.get(user));
//        }
//    }
}