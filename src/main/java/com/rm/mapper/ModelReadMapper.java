package com.rm.mapper;

import com.rm.dto.ModelReadDto;
import com.rm.entity.Model;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModelReadMapper implements Mapper<Model, ModelReadDto> {

    private final ManufacturerReadMapper manufacturerReadMapper;
    private final VehicleTypeReadMapper vehicleTypeReadMapper;
    private final DiscountReadMapper discountReadMapper;

    @Override
    public ModelReadDto mapFrom(Model object) {
        return new ModelReadDto(
                object.getId(),
                object.getModel(),
                Optional.ofNullable(object.getManufacturer())
                        .map(manufacturerReadMapper::mapFrom)
                        .orElse(null),
                object.getProductionYear(),
                Optional.ofNullable(object.getVehicleType())
                        .map(vehicleTypeReadMapper::mapFrom)
                        .orElse(null),
                object.getPrice(),
                Optional.ofNullable(object.getDiscount())
                        .map(discountReadMapper::mapFrom)
                        .orElse(null)
        );
    }
}

