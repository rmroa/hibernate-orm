package com.rm.dao;

import com.rm.entity.Model;
import org.hibernate.SessionFactory;

public class ModelRepository extends BaseRepository<Long, Model> {

    public ModelRepository(SessionFactory sessionFactory) {
        super(Model.class, sessionFactory);

    }

}
