package com.rm.dao;

import com.rm.entity.Manufacturer;

import javax.persistence.EntityManager;

public class ManufacturerRepository extends BaseRepository<Integer, Manufacturer> {

    public ManufacturerRepository(EntityManager entityManager) {
        super(Manufacturer.class, entityManager);
    }
}
