package com.rm.dao;

import com.rm.entity.VehicleType;

import javax.persistence.EntityManager;

public class VehicleTypeRepository extends BaseRepository<Long, VehicleType> {

    public VehicleTypeRepository(EntityManager entityManager) {
        super(VehicleType.class, entityManager);
    }
}
