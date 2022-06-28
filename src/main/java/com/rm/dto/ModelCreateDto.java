package com.rm.dto;

import com.rm.entity.DriveUnit;
import com.rm.entity.EngineType;
import com.rm.entity.Transmission;
import com.rm.validation.UpdateCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public final class ModelCreateDto {

    @NotNull
    private final String model;

    private final Integer manufacturerId;

    private final LocalDate productionYear;

    private final Long vehicleTypeId;

    @NotNull(groups = UpdateCheck.class)
    private final Transmission transmission;

    @NotNull(groups = UpdateCheck.class)
    private final DriveUnit driveUnit;

    @NotNull(groups = UpdateCheck.class)
    private final EngineType engineType;

    private final Long currentMileage;

    private final BigDecimal price;
}
