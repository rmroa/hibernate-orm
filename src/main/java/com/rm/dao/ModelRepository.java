package com.rm.dao;

import com.rm.entity.Model;

import javax.persistence.EntityManager;

public class ModelRepository extends BaseRepository<Long, Model> {

    public ModelRepository(EntityManager entityManager) {
        super(Model.class, entityManager);

    }

}
