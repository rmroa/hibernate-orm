package com.rm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
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

    @Embedded
    @AttributeOverride(name = "firstName", column = @Column(name = "first_name"))
    @AttributeOverride(name = "lastName", column = @Column(name = "last_name"))
    private PersonalInfo personalInfo;

//    @Type(type = "rm")
//    @Type(type = "com.vladmihalcea.hibernate.type.json.JsonBinaryType")
    // можем использовать "jsonb" т.к. метод JsonBinaryType getName() возвращает "jsonb"
//    private String info;

    @Enumerated(EnumType.STRING)
    private Role role;


}
