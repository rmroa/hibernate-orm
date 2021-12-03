package com.rm;

import com.rm.entity.Birthday;
import com.rm.entity.Gender;
import com.rm.entity.Role;
import com.rm.entity.User;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

class HibernateRunnerTest {

    @Test
    void checkGetReflectionApi() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.getLong("id");
        resultSet.getString("first_name");
        resultSet.getString("last_name");
        resultSet.getDate("birthday").toLocalDate();
        resultSet.getString("image");
        resultSet.getString("country");
        resultSet.getString("city");
        resultSet.getString("phone");
        resultSet.getString("email");
        resultSet.getString("password");
        Role.valueOf(resultSet.getString("role"));
        Gender.valueOf(resultSet.getString("gender"));

        Class<User> clazz = User.class;

        Constructor<User> constructor = clazz.getConstructor();
        User user = constructor.newInstance();
        Field firstNameField = clazz.getDeclaredField("firstName");
        firstNameField.setAccessible(true);
        firstNameField.set(user, resultSet.getString("first_name"));
    }

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .birthday(new Birthday(LocalDate.of(2000, 1, 20)))
                .image("")
                .country("Belarus")
                .city("Brest")
                .phone("375292901210")
                .email("firstname@mail.ru")
                .password("password")
                .role(Role.valueOf("USER"))
                .gender(Gender.valueOf("MALE"))
                .build();

        String sql = "" +
                "INSERT INTO " +
                "%s " +
                "(%s) " +
                "VALUES (%s)";

        String tableName = ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());

        Field[] declaredFields = user.getClass().getDeclaredFields();

        String columnsNames = Arrays.stream(declaredFields)
                .map(field -> ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(joining(", "));

        String columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(joining(", "));

        System.out.println(format(sql, tableName, columnsNames, columnValues));

        Connection connection = null;
        PreparedStatement preparedStatement = connection.prepareStatement(format(sql, tableName, columnsNames, columnValues));
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            preparedStatement.setObject(1, declaredField.get(user));
        }
    }
}