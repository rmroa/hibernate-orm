package com.rm.mapper;

import com.rm.dao.ManufacturerRepository;
import com.rm.dao.VehicleTypeRepository;
import com.rm.dto.ModelCreateDto;
import com.rm.entity.Model;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ModelCreateMapper implements Mapper<ModelCreateDto, Model> {

    private final ManufacturerRepository manufacturerRepository;
    private final VehicleTypeRepository vehicleTypeRepository;

    @Override
    public Model mapFrom(ModelCreateDto object) {
        return Model.builder()
                .model(object.getModel())
                .manufacturer(manufacturerRepository.findById(object.getManufacturerId()).orElseThrow(IllegalArgumentException::new))
                .productionYear(object.getProductionYear())
                .vehicleType(vehicleTypeRepository.findById(object.getVehicleTypeId()).orElseThrow(IllegalArgumentException::new))
                .transmission(object.getTransmission())
                .driveUnit(object.getDriveUnit())
                .engineType(object.getEngineType())
                .currentMileage(object.getCurrentMileage())
                .price(object.getPrice())
                .build();
    }
}

