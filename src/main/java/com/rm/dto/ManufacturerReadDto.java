package com.rm.dto;

import com.rm.entity.LocaleInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public final class ManufacturerReadDto {

    private final Integer id;

    private final String brand;

    private final String country;

    private final List<LocaleInfo> locales;
}
