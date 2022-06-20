package com.rm.mapper;

import com.rm.dto.ManufacturerReadDto;
import com.rm.entity.Manufacturer;
import org.hibernate.Hibernate;

public class ManufacturerReadMapper implements Mapper<Manufacturer, ManufacturerReadDto> {

    @Override
    public ManufacturerReadDto mapFrom(Manufacturer object) {
        Hibernate.initialize(object.getLocales());
        return new ManufacturerReadDto(
                object.getId(),
                object.getBrand(),
                object.getCountry(),
                object.getLocales()
        );
    }
}
