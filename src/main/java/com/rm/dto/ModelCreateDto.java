package com.rm.dto;

import com.rm.entity.DriveUnit;
import com.rm.entity.EngineType;
import com.rm.entity.Transmission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public final class ModelCreateDto {

    private final String model;

    private final Integer manufacturerId;

    private final LocalDate productionYear;

    private final Long vehicleTypeId;

    private final Transmission transmission;

    private final DriveUnit driveUnit;

    private final EngineType engineType;

    private final Long currentMileage;

    private final BigDecimal price;
}
