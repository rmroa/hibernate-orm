package com.rm.dto;

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
public final class ModelReadDto {

    private final Long id;

    private final String model;

    private final ManufacturerReadDto manufacturer;

    private final LocalDate productionYear;

    private final VehicleTypeReadDto vehicleType;

    private final BigDecimal price;

    private final DiscountReadDto discount;
}
