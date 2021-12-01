package com.rm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@EqualsAndHashCode
public class Birthday {

    private final LocalDate birthday;

    public Birthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate birthday() {
        return birthday;
    }

    public long getAge() {
        return ChronoUnit.YEARS.between(birthday, LocalDate.now());
    }
}
