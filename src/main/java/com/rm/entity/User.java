package com.rm.entity;

import com.rm.converter.BirthdayConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
//@TypeDef(name = "rm", typeClass = JsonBinaryType.class)
public class User {

    @Id
//    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Convert(converter = BirthdayConverter.class)
    private Birthday birthday;

    private String image;

    private String country;

    private String city;

    private String phone;

    private String email;

    private String password;

//    @Type(type = "rm")
//    @Type(type = "com.vladmihalcea.hibernate.type.json.JsonBinaryType")
    // можем использовать "jsonb" т.к. метод JsonBinaryType getName() возвращает "jsonb"
//    private String info;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
