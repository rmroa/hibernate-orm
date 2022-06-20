package com.rm.mapper;

import com.rm.dto.VehicleTypeReadDto;
import com.rm.entity.VehicleType;

public class VehicleTypeReadMapper implements Mapper<VehicleType, VehicleTypeReadDto> {

    @Override
    public VehicleTypeReadDto mapFrom(VehicleType object) {
        return new VehicleTypeReadDto(
                object.getId(),
                object.getType()
        );
    }
}
