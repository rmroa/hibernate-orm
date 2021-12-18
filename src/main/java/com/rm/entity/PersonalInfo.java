package com.rm.entity;

import com.rm.converter.BirthdayConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo {

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

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
